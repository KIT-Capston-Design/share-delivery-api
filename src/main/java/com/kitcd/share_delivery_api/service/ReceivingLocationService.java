package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.dto.receivinglocation.ReceivingLocationDTO;
import com.kitcd.share_delivery_api.utils.geometry.Location;

public interface ReceivingLocationService {
    ReceivingLocation findByReceivingLocationId(Long id);
    ReceivingLocationDTO enrollReceivingLocation(Account account, ReceivingLocationDTO dto);
}
