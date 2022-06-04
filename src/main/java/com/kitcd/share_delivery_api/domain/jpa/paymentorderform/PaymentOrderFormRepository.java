package com.kitcd.share_delivery_api.domain.jpa.paymentorderform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentOrderFormRepository extends JpaRepository<PaymentOrderForm, Long> {

    @Query("select pf from PaymentOrderForm pf join fetch pf.imageFile join pf.payment p " +
            "where p.paymentId = :paymentId")
    List<PaymentOrderForm> getAllByPaymentId(Long paymentId);
}
