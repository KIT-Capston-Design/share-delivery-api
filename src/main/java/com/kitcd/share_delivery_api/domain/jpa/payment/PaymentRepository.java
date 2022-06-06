package com.kitcd.share_delivery_api.domain.jpa.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select p from Payment p join fetch p.deliveryRoom left join fetch p.paymentDiscounts " +
            "where p.deliveryRoom.deliveryRoomId = :deliveryRoomId")
    Payment getByDeliveryRoomId(Long deliveryRoomId);

}
