package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformation;
import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformationRedisRepository;
import com.kitcd.share_delivery_api.service.LoggedOnInformationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
@Slf4j
public class LoggedOnInformationServiceImpl implements LoggedOnInformationService {

    private final LoggedOnInformationRedisRepository loggedOnInformationRedisRepository;

    @Override
    public LoggedOnInformation findLoggedOnInformationByAccountId(Long accountId) {

        LoggedOnInformation loggedOnInformationByAccountId = loggedOnInformationRedisRepository.findLoggedOnInformationByAccountId(accountId);

        if(loggedOnInformationByAccountId == null) throw new EntityNotFoundException("LoggedOnInformation");

        return loggedOnInformationByAccountId;
    }

    @Override
    public String getFcmTokenByAccountId(Long userId) {
        return findLoggedOnInformationByAccountId(userId).getFcmToken();
    }

    @Override
    public void save(LoggedOnInformation loggedOnInformation) {
        loggedOnInformationRedisRepository.save(loggedOnInformation);
    }
}
