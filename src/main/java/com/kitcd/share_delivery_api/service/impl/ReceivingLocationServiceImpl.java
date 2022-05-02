package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocationRepository;
import com.kitcd.share_delivery_api.service.ReceivingLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReceivingLocationServiceImpl implements ReceivingLocationService {

    private final ReceivingLocationRepository receivingLocationRepository;

    @Override
    public ReceivingLocation getReceivingLocationByNameAndCoordinate(String name, Coordinate location) {
        return receivingLocationRepository.findByNameAndCoordinate(name, location);
    }

    @Override
    public ReceivingLocation enrollReceivingLocation(Account account, Coordinate coordinate) {
        ReceivingLocation receivingLocation = ReceivingLocation.builder()
                .account(account)
                .coordinate(coordinate)
                .build();
        receivingLocationRepository.save(receivingLocation);
        return  receivingLocation;
    }

}
