package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfo;
import com.kitcd.share_delivery_api.domain.redis.deliveryroom.ActivatedDeliveryRoomInfoRedisRepository;
import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.fcm.FCMGroupRequest;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.dto.payment.FinalOrderInformationDTO;
import com.kitcd.share_delivery_api.service.*;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryRoomServiceImpl implements DeliveryRoomService {

    private final DeliveryRoomRepository deliveryRoomRepository;
    private final EntryOrderService entryOrderService;
    private final LoggedOnInformationService loggedOnInformationService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final PaymentService paymentService;
    private final PaymentOrderFormService paymentOrderFormService;
    private final ActivatedDeliveryRoomInfoRedisRepository activatedDeliveryRoomInfoRedisRepository;


    @Override
    public FinalOrderInformationDTO getFinalOrderInformation(Long deliveryRoomId) {

        DeliveryRoom deliveryRoom = findByDeliveryRoomId(deliveryRoomId);

        if(
                !(
                        deliveryRoom.getStatus().equals(DeliveryRoomState.WAITING_DELIVERY)
                                || deliveryRoom.getStatus().equals(DeliveryRoomState.WAITING_REMITTANCE)
                                || deliveryRoom.getStatus().equals(DeliveryRoomState.COMPLETED)
                )
        ) throw new IllegalStateException("최종 주문 정보를 조회할 수 없습니다.");


        Payment payment = paymentService.getByDeliveryRoomId(deliveryRoomId);
        List<OrderResDTO> orders = entryOrderService.getAcceptedOrderInformation(deliveryRoomId);
        List<String> imageUrls = paymentOrderFormService.getOrderFormImagesByPaymentId(payment.getPaymentId());

        return deliveryRoom.toFinalOrderInformationDTO(payment, orders, imageUrls);
    }


    @Override
    public DeliveryRoomDTO getDeliveryRoomDTO(Long deliveryRoomId) {

        DeliveryRoom deliveryRoom = deliveryRoomRepository.getDeliveryRoomByDeliveryRoomId(deliveryRoomId);

        if(deliveryRoom == null) throw new EntityNotFoundException(DeliveryRoom.class.toString());

        return deliveryRoom.toDTO();
    }

    @Override
    public List<DeliveryRoomDTO> getDeliveryRooms(Location location, Double distance){
        return deliveryRoomRepository.findDeliveryRoomDTOWithLocation(location, distance);
    }

    @Override
    public List<ParticipatedDeliveryRoomDTO> getDeliveryHistory(Long accountId) {
        return deliveryRoomRepository.getDeliveryHistory(accountId);
    }

    @Override
    public DeliveryRoom findByLeaderId(Long accountId) {

        DeliveryRoom deliveryRoom = deliveryRoomRepository.findByLeader_AccountId(accountId);

        if(deliveryRoom == null) throw new EntityNotFoundException(DeliveryRoom.class.toString());

        return deliveryRoom;
    }

    @Override
    public DeliveryRoom findByDeliveryRoomId(Long roomId) {
        DeliveryRoom deliveryRoom = deliveryRoomRepository.findByDeliveryRoomId(roomId);

        if(deliveryRoom == null) throw new EntityNotFoundException(DeliveryRoom.class.toString());

        return deliveryRoom;
    }


    @Override
    public List<ParticipatedDeliveryRoomDTO> getParticipatingActivatedDeliveryRoom(Long accountId) {
        return deliveryRoomRepository.getParticipatingActivatedDeliveryRoom(accountId);
    }

    @Override
    public DeliveryRoom deliveryRoomCreate(DeliveryRoom deliveryRoom, List<OrderMenuRequestDTO> menuList) {

        DeliveryRoom room = deliveryRoomRepository.save(deliveryRoom);

        entryOrderService.enrollEntryOrder(room, menuList, EntryOrderType.LEAD, State.ACCEPTED);

        return room;
    }

    @Override
    public List<String> getParticipantFCMTokens(Long roomId, State state){

        List<Long> participantsIds = getParticipantsIds(roomId, state);

        return participantsIds.stream()
                .map(loggedOnInformationService::getFcmTokenByAccountId)
                .collect(Collectors.toList());
    }

    @Override // state : 참여자의 주문 처리 상태에 따라 조회 ex) ACCEPTED, PENDING
    public List<Long> getParticipantsIds(Long roomId, State state){

        List<Long> participantsIds = deliveryRoomRepository.getParticipantsIds(roomId, state);

        if(participantsIds == null) throw new IllegalArgumentException("해당 delivery room id에 대한 참여자 ids 가져오기 실패");

        return participantsIds;
    }

    @Override
    public DeliveryRoom closeRecruit(Long deliveryRoomId) {

        // 모집글 상태 변경
        DeliveryRoom deliveryRoom = findByDeliveryRoomId(deliveryRoomId);

        //클라이언트가 방장인지 체크. 방장이 아닐 경우 AccessDeniedException
        deliveryRoom.checkLeader(ContextHolder.getAccountId());

        deliveryRoom.closeRecruit();

        // 참여자 상태 변경 Pending -> Accepted
        entryOrderService.acceptOrders(deliveryRoomId);


        //  파이어베이스에 FCM 그룹 생성 요청 보내고 그룹 토큰 반환받는다. // throwable JSONException, IOException
        String fcmGroupToken = firebaseCloudMessageService.sendGroupRequest(
                FCMGroupRequest.Type.create,
                "DeliveryRoom_" + deliveryRoomId.toString(),
                null, //생성 시에는 그룹 키 null로 전송
                getParticipantFCMTokens(deliveryRoomId, State.ACCEPTED) //모집글에 참여한 유저 토큰들 받아서 넘겨주기
        );

        //그룹 토큰 통해 해당 모집글 참여자들에게 메시지 전송
        Map<String, Object> data = new HashMap<>();
        data.put("type", FCMDataType.CLOSE_RECRUIT);
        data.put("roomId", deliveryRoomId);
        firebaseCloudMessageService.sendMessageTo(
                fcmGroupToken, "해당 모집글의 인원 모집이 종료되었습니다. 이제 주문이 시작됩니다.", deliveryRoom.getContent(), data
        );

        //redis에 '모집글 - 그룹fcm토큰' 형식으로 저장
        activatedDeliveryRoomInfoRedisRepository.save(
                ActivatedDeliveryRoomInfo.builder()
                        .deliveryRoomId(deliveryRoomId)
                        .fcmGroupToken(fcmGroupToken)
                        .users(getParticipantsIds(deliveryRoomId, State.ACCEPTED))
                        .build()
        );

        return deliveryRoom;
    }

    @Override
    public Long deleteDeliveryRoom(Long deliveryRoomId) {

        DeliveryRoom deliveryRoom = findByDeliveryRoomId(deliveryRoomId);

        deliveryRoom.checkLeader(ContextHolder.getAccountId());

        DeliveryRoomState status = deliveryRoom.getStatus(); //삭제 이전의 상태 저장

        deliveryRoom.delete();

        //fcm 메시지 발송
        State participantsOrderStatus = (status == DeliveryRoomState.OPEN) ? State.PENDING : State.ACCEPTED;
        List<String> participantFCMTokens = getParticipantFCMTokens(deliveryRoomId, participantsOrderStatus);
        String leaderFcmToken = loggedOnInformationService.getFcmTokenByAccountId(ContextHolder.getAccountId());

        //TODO: FCM 레거시 API 활용하여 여러 사용자에 한 번에 전송하도록 추후 개선 필요.
        for(String token : participantFCMTokens){
            if(!leaderFcmToken.equals(token)) //리더가 아닌 유저에게만 push
                firebaseCloudMessageService.sendMessageTo(token, "참여한 모집글이 삭제되었습니다.", deliveryRoom.getContent(), null);
        }

        return deliveryRoomId;
    }

    @Override
    public Long exitDeliveryRoom(Long deliveryRoomId) {

        DeliveryRoom deliveryRoom = findByDeliveryRoomId(deliveryRoomId);
        EntryOrder entryOrder = entryOrderService.findByAccountIdAndDeliveryRoomId(ContextHolder.getAccountId(), deliveryRoomId);

        if(deliveryRoom.getStatus() != DeliveryRoomState.OPEN){
            throw new IllegalStateException("인원 모집이 마감된 이후에는 퇴장할 수 없습니다.");
        }

        entryOrder.exitDeliveryRoom();

        //리더에게 fcm 푸시메시지 발송
        String leaderFcmToken = loggedOnInformationService.getFcmTokenByAccountId(deliveryRoom.getLeader().getAccountId());

        Map<String, Object> fcmData = new HashMap<>();
        fcmData.put("type", FCMDataType.EXIT_ROOM);
        fcmData.put("roomId", deliveryRoomId);

        firebaseCloudMessageService.sendMessageTo(
                leaderFcmToken,
                ContextHolder.getAccount().getNickname()+ " 유저가 방을 나갔습니다",
                null,
                fcmData
        );

        return deliveryRoomId;
    }



}
