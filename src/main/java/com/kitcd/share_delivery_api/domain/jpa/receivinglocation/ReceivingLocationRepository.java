package com.kitcd.share_delivery_api.domain.jpa.receivinglocation;


import com.kitcd.share_delivery_api.utils.geometry.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivingLocationRepository extends JpaRepository<ReceivingLocation, Long> {
    ReceivingLocation findByDescriptionAndCoordinate(String description, Location coordinate);
}
