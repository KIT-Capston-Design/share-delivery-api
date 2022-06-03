package com.kitcd.share_delivery_api.dto.payment;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.dto.paymentdiscount.PaymentDiscountEnrollRequestDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEnrollRequestDTO {
    private Long deliveryTip;
    private List<PaymentDiscountEnrollRequestDTO> discounts;

    public Payment toEntity(DeliveryRoom deliveryRoom){
        return Payment.builder().deliveryRoom(deliveryRoom)
                .deliveryFee(deliveryTip)
                .build();

    }
}
