package com.kitcd.share_delivery_api.domain.jpa.remittance;

import com.kitcd.share_delivery_api.dto.remittance.RemittanceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {

    @Query("select new com.kitcd.share_delivery_api.dto.remittance.RemittanceDTO" +
            "(r.remittanceId, ac.accountId, ac.nickname, r.amount, r.isRemitted) " +
            "from Remittance r join r.remitter ac join r.payment p " +
            "where p.deliveryRoom.deliveryRoomId = :deliveryRoomId")
    List<RemittanceDTO> getRemittanceDTOsByDeliveryRoomId(Long deliveryRoomId);


    @Query("select r from Remittance r join r.payment p " +
            "where p.deliveryRoom.deliveryRoomId = :deliveryRoomId")
    List<Remittance> getRemittancesByDeliveryRoomId(Long deliveryRoomId);

    Remittance findRemittanceByRemittanceId(Long remittanceId);

    @Query("select count(r) from Remittance r join r.payment p " +
            "where r.isRemitted = true and p.deliveryRoom.deliveryRoomId = :deliveryRoomId")
    Long getNumberOfPeopleRemittedWith(Long deliveryRoomId);
}
