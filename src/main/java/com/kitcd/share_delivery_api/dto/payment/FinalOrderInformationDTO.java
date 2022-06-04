package com.kitcd.share_delivery_api.dto.payment;


import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.DeliveryRoomState;
import com.kitcd.share_delivery_api.domain.jpa.deliveryroom.PlatformType;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import com.kitcd.share_delivery_api.dto.account.SimpleAccountDTO;
import com.kitcd.share_delivery_api.dto.common.LocationDTO;
import com.kitcd.share_delivery_api.dto.entryorder.OrderResDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalOrderInformationDTO {

    private Long roomId;

    private SimpleAccountDTO leader;

    private List<OrderResDTO> orders;

    private List<PaymentDiscount> discounts;

//    private Long totalOrderMoney;

    private Long deliveryFee;

    private Long totalDiscountAmount;

    private LocationDTO receivingLocation;

    private List<String> orderFormUrlList;

}