package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocationRepository;
import com.kitcd.share_delivery_api.dto.receivinglocation.ReceivingLocationDTO;
import com.kitcd.share_delivery_api.service.ReceivingLocationService;
import com.kitcd.share_delivery_api.utils.geometry.Location;
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
    public ReceivingLocation findByReceivingLocationId(Long id) {
        return receivingLocationRepository.findByReceivingLocationId(id);
    }

    @Override
    public ReceivingLocationDTO enrollReceivingLocation(Account account, ReceivingLocationDTO dto) {
        return new ReceivingLocationDTO(receivingLocationRepository.save(dto.toEntity(account)));
    }

}
