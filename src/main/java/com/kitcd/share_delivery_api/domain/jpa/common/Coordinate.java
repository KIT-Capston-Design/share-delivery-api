package com.kitcd.share_delivery_api.domain.jpa.common;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@Getter

@NoArgsConstructor
@AllArgsConstructor

@ToString

@Embeddable
public class Coordinate {
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

}
