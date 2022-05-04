package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.dto.receivinglocation.ReceivingLocationDTO;

public interface ReceivingLocationService {

    ReceivingLocation getReceivingLocationByNameAndCoordinate(String name, Coordinate location);
    ReceivingLocationDTO enrollReceivingLocation(Account account, ReceivingLocationDTO dto);
}