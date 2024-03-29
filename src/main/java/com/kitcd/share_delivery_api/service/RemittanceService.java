package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;
import com.kitcd.share_delivery_api.dto.remittance.RemittanceDTO;

import java.util.List;

public interface RemittanceService {
    Remittance findRemittanceByRemittanceId(Long remittanceId);

    List<Remittance> saveAll(List<Remittance> remittances);

    List<Remittance> createRemittanceEntities(DeliveryRoom room, Payment payment, long discountPerPerson);

    List<RemittanceDTO> getRemittanceDTOsByDeliveryRoomId(Long deliveryRoomId);

    List<Remittance> getRemittancesByDeliveryRoomId(Long deliveryRoomId);

    long getNumberOfPeopleRemittedWith(Long deliveryRoomId);

    boolean approveRemittance(Long remittanceId, DeliveryRoom deliveryRoom);
}
