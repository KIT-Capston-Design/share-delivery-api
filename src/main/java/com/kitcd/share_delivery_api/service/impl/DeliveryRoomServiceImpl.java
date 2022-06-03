package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.dto.deliveryroom.DeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.deliveryroom.ParticipatedDeliveryRoomDTO;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.ordermenu.OrderMenuRequestDTO;
import com.kitcd.share_delivery_api.service.DeliveryRoomService;
import com.kitcd.share_delivery_api.service.FirebaseCloudMessageService;
import com.kitcd.share_delivery_api.service.LoggedOnInformationService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrderType;
import com.kitcd.share_delivery_api.service.EntryOrderService;
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

    @Override
    public DeliveryRoomDTO getDeliveryRoomDTO(Long deliveryRoomId) {

        DeliveryRoom deliveryRoom = deliveryRoomRepository.getDeliveryRoomByDeliveryRoomId(deliveryRoomId);

        if(deliveryRoom == null) throw new EntityNotFoundException(DeliveryRoom.class.toString());

        return deliveryRoom.toDTO();
    }

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
        deliveryRoom.closeRecruit();


        // 참여자 상태 변경 Pending -> Accepted
        entryOrderService.acceptOrders(deliveryRoomId);

        return deliveryRoom;
    }

    @Override
    public Long deleteDeliveryRoom(Long deliveryRoomId) {

        DeliveryRoom deliveryRoom = findByDeliveryRoomId(deliveryRoomId);

        deliveryRoom.checkLeader(ContextHolder.getAccountId());

        deliveryRoom.delete();

        //fcm 메시지 발송
        State participantsOrderStatus = (deliveryRoom.getStatus() == DeliveryRoomState.OPEN) ? State.PENDING : State.ACCEPTED;
        List<String> participantFCMTokens = getParticipantFCMTokens(deliveryRoomId, participantsOrderStatus);

        //TODO: FCM 레거시 API 활용하여 여러 사용자에 한 번에 전송하도록 추후 개선 필요.
        participantFCMTokens.forEach(
                token -> firebaseCloudMessageService.sendMessageTo(token, "참여한 모집글이 삭제되었습니다.", deliveryRoom.getContent(), null)
        );

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
