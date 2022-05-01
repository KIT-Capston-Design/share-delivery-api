package com.kitcd.share_delivery_api.utils.geometry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@Builder
@Getter
public class Location {

    private Double latitude;
    private Double longitude;

}