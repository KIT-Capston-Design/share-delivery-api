package com.kitcd.share_delivery_api.domain.jpa.post;

import com.kitcd.share_delivery_api.dto.post.PostListDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepositoryCustom {

    List<PostListDTO> findPostListDTOWithLocationAndPagingWithLastCreatedDateTime(Location location, Long radius, LocalDateTime lastCreatedDateTime);

    List<PostListDTO> findPostListDTOWithLocationAndPagingWithLastCreatedDateTimeAndFiltWithCategoryName(Location location, Long radius, LocalDateTime lastCreatedDateTime, String categoryName);
}
