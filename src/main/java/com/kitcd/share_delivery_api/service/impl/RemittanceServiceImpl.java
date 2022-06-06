package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomRepository;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.domain.jpa.remittance.RemittanceRepository;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.remittance.RemittanceDTO;
import com.kitcd.share_delivery_api.service.ActivatedDeliveryRoomInfoRedisService;
import com.kitcd.share_delivery_api.service.FirebaseCloudMessageService;
import com.kitcd.share_delivery_api.service.LoggedOnInformationService;
import com.kitcd.share_delivery_api.service.RemittanceService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RemittanceServiceImpl implements RemittanceService {
    private final RemittanceRepository remittanceRepository;
    private final DeliveryRoomRepository deliveryRoomRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final ActivatedDeliveryRoomInfoRedisService activatedDeliveryRoomInfoRedisService;

    @Override
    public List<RemittanceDTO> getRemittanceDTOsByDeliveryRoomId(Long deliveryRoomId) {
        List<RemittanceDTO> list = remittanceRepository.getRemittanceDTOsByDeliveryRoomId(deliveryRoomId);

        if(list == null || list.isEmpty()) throw new EntityNotFoundException(Remittance.class.toString());

        return list;
    }

    @Override
    public List<Remittance> getRemittancesByDeliveryRoomId(Long deliveryRoomId) {
        List<Remittance> list = remittanceRepository.getRemittancesByDeliveryRoomId(deliveryRoomId);

        if(list == null || list.isEmpty()) throw new EntityNotFoundException(Remittance.class.toString());

        return list;
    }

    @Override
    public long getNumberOfPeopleRemittedWith(Long deliveryRoomId) {
        return remittanceRepository.getNumberOfPeopleRemittedWith(deliveryRoomId);
    }

    @Override
    public boolean approveRemittance(Long remittanceId, DeliveryRoom deliveryRoom) {

        deliveryRoom.checkLeader(ContextHolder.getAccountId());
        if(!deliveryRoom.getStatus().equals(DeliveryRoomState.WAITING_PAYMENT))
            throw new IllegalStateException("송금대기 상태가 아니기에 송금 처리를 수행할 수 없습니다.");

        Remittance remittance = findRemittanceByRemittanceId(remittanceId);

        boolean result = remittance.approve();

        remittanceRepository.save(remittance);

        long numberOfPeopleRemitted = getNumberOfPeopleRemittedWith(deliveryRoom.getDeliveryRoomId());

        //주도자를 제외한 인원이 송금하는 인원이기에 -1
        if(deliveryRoom.getPeopleNumber() - 1 == numberOfPeopleRemitted){
            //모두 송금 완료됐을 경우 모집글 상태변경, 참여자들에게 FCM 송신
            deliveryRoom.remittancesComplete();
            deliveryRoomRepository.save(deliveryRoom);

            //FCM
            Map<String, Object> data = new HashMap<>();
            data.put("type", FCMDataType.COMPLETE_DELIVERY_ROOM);
            data.put("roomId", deliveryRoom.getDeliveryRoomId());

            String groupFcmToken = activatedDeliveryRoomInfoRedisService.getGroupFcmToken(deliveryRoom.getDeliveryRoomId());
            firebaseCloudMessageService.sendMessageTo(
                    groupFcmToken,
                    deliveryRoom.getContent(),
                    "공유 배달이 모두 완료되었습니다.",
                    data
            );
        }

        return result;
    }

    @Override
    public Remittance findRemittanceByRemittanceId(Long remittanceId){
        Remittance remittance = remittanceRepository.findRemittanceByRemittanceId(remittanceId);

        if(remittance == null) throw new FetchNotFoundException(Remittance.class.toString(), remittanceId);

        return remittance;
    }

    @Override
    public List<Remittance> saveAll(List<Remittance> remittances) {
        return remittanceRepository.saveAll(remittances);
    }

    @Override
    public List<Remittance> createRemittanceEntities(DeliveryRoom room, Payment payment, long totalDiscountAmount) {

        long additionalAmount = payment.getDeliveryFee() - totalDiscountAmount;
        long additionalAmountPerPeople = additionalAmount / room.getPeopleNumber();

        //Remittance 엔티티 생성
        // 참여자들의 주문 가져오기
        List<EntryOrder> orders = room.getParticipantsOrder();
        List<Remittance> remittances = new ArrayList<>();
        orders.forEach(order -> remittances.add(order.toRemittanceEntity(room.getLeader(), payment, additionalAmountPerPeople)));

        return saveAll(remittances);
    }


}
