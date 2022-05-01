package com.kitcd.share_delivery_api.dto.common;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {
    @NotBlank
    private Double latitude;
    @NotBlank
    private Double longitude;
    private String description;
}
