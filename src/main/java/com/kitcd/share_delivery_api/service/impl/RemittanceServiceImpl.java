package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.entryorder.EntryOrder;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.domain.jpa.remittance.RemittanceRepository;
import com.kitcd.share_delivery_api.dto.remittance.RemittanceDTO;
import com.kitcd.share_delivery_api.service.RemittanceService;
import com.kitcd.share_delivery_api.utils.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RemittanceServiceImpl implements RemittanceService {
    private final RemittanceRepository remittanceRepository;

    @Override
    public List<RemittanceDTO> getRemittanceDTOsByDeliveryRoomId(Long deliveryRoomId) {
        return remittanceRepository.getRemittanceDTOsByDeliveryRoomId(deliveryRoomId);
    }

    @Override
    public boolean approveRemittance(Long remittanceId, DeliveryRoom deliveryRoom) {

        deliveryRoom.checkLeader(ContextHolder.getAccountId());

        Remittance remittance = findRemittanceByRemittanceId(remittanceId);

        return remittance.approve();
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
