package com.kitcd.share_delivery_api.dto.paymentdiscount;

import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDiscountEnrollRequestDTO {
    private String paymentDiscountName;
    private Long amount;

    public PaymentDiscount toEntity(Payment payment){
        return PaymentDiscount.builder()
                .paymentDiscountName(paymentDiscountName)
                .payment(payment)
                .amount(amount)
                .build();
    }
}
