package com.kitcd.share_delivery_api.dto.receivinglocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingLocationDTO {
    private String description;
    private Double lat;
    private Double lng;
}
