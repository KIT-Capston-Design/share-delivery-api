package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocationRepository;
import com.kitcd.share_delivery_api.dto.receivinglocation.ReceivingLocationDTO;
import com.kitcd.share_delivery_api.service.ReceivingLocationService;
import com.kitcd.share_delivery_api.utils.address.FindAddressWithLocation;
import com.kitcd.share_delivery_api.utils.geometry.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReceivingLocationServiceImpl implements ReceivingLocationService {

    private final ReceivingLocationRepository receivingLocationRepository;
    private final FindAddressWithLocation findAddressWithLocation;

    @Override
    public ReceivingLocation findByReceivingLocationId(Long id) {

        ReceivingLocation result = receivingLocationRepository.findByReceivingLocationId(id);

        if ( result == null )
            throw new EntityNotFoundException(ReceivingLocation.class + "Entity Is Not Found");

        return result;
    }

    @Override
    public ReceivingLocationDTO enrollReceivingLocation(Account account, ReceivingLocationDTO dto) {
        dto.setAddress(findAddressWithLocation.coordToAddr(new Location(dto.getLatitude(), dto.getLongitude())));

        return new ReceivingLocationDTO(receivingLocationRepository.save(dto.toEntity(account)));
    }

}
