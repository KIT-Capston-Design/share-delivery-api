package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;

public interface ReceivingLocationService {

    ReceivingLocation getReceivingLocationByNameAndCoordinate(String name, Coordinate location);
    ReceivingLocation enrollReceivingLocation(Account account, Coordinate coordinate);
}
