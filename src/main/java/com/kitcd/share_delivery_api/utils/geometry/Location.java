package com.kitcd.share_delivery_api.utils.geometry;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Embeddable
public class Location {

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

}