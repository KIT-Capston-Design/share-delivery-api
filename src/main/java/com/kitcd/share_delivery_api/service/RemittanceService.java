package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.remittance.Remittance;

import java.util.List;

public interface RemittanceService {
    List<Remittance> saveAll(List<Remittance> remittances);

    List<Remittance> createRemittanceEntities(DeliveryRoom room, Payment payment, long discountPerPerson);
}
