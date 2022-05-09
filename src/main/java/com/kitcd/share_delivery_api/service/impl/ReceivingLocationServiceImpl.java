package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.account.Account;
import com.kitcd.share_delivery_api.domain.jpa.common.Coordinate;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocation;
import com.kitcd.share_delivery_api.domain.jpa.receivinglocation.ReceivingLocationRepository;
import com.kitcd.share_delivery_api.dto.receivinglocation.ReceivingLocationDTO;
import com.kitcd.share_delivery_api.service.ReceivingLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReceivingLocationServiceImpl implements ReceivingLocationService {

    private final ReceivingLocationRepository receivingLocationRepository;

    @Override
    public ReceivingLocation getReceivingLocationById(Long id) throws IllegalStateException{
        Optional<ReceivingLocation> data = receivingLocationRepository.findById(id);
        if(data.isEmpty()){
            throw new IllegalStateException("아이디가 적합하지 않습니다.");
        }else{
            return data.get();
        }
    }

    @Override
    public ReceivingLocationDTO enrollReceivingLocation(Account account, ReceivingLocationDTO dto) {
        ReceivingLocation receivingLocation = dto.toEntity(account);
        receivingLocationRepository.save(receivingLocation);
        return  new ReceivingLocationDTO(receivingLocation);
    }

}
