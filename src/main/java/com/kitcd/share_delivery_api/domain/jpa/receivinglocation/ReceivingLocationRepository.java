package com.kitcd.share_delivery_api.domain.jpa.receivinglocation;

import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivingLocationRepository extends JpaRepository<ReceivingLocation, Long> {
}
