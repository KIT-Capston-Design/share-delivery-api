package com.kitcd.share_delivery_api.dto.deliveryroom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateDeliveryRoomDTO {
    private String content;
    private Long deliveryTip;
    private Long limitPerson;
}
