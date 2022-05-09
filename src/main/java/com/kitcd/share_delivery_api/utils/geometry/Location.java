package com.kitcd.share_delivery_api.utils.geometry;

import lombok.*;

import javax.persistence.Embeddable;

@ToString
@AllArgsConstructor
@Builder
@Getter
@Embeddable
public class Location {

    private Double latitude;
    private Double longitude;

}