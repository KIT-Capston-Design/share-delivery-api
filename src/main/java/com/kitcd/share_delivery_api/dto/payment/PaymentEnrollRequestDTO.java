package com.kitcd.share_delivery_api.dto.payment;

import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoom;
import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.dto.paymentdiscount.PaymentDiscountEnrollRequestDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEnrollRequestDTO {

    @NotNull
    private Long deliveryFee;

    private List<PaymentDiscountEnrollRequestDTO> discounts;

    public Payment toEntity(DeliveryRoom deliveryRoom){
        return Payment.builder()
                .deliveryFee(deliveryFee)
                .deliveryRoom(deliveryRoom)
                .build();

    }
}
