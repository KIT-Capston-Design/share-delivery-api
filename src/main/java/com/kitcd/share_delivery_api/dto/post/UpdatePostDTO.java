package com.kitcd.share_delivery_api.dto.post;

import com.kitcd.share_delivery_api.dto.placeshare.PlaceShareDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostDTO {
    private Location coordinate;
    private String content;
    private String category;
    private PlaceShareDTO sharePlace;
    private List<String> deletedImages;

}
