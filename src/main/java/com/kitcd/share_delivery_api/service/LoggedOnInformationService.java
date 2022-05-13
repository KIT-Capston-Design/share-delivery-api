package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformation;

public interface LoggedOnInformationService {
    LoggedOnInformation findLoggedOnInformationByAccountId(Long accountId);

    String getFcmTokenByAccountId(Long userId);

    void save(LoggedOnInformation loggedOnInformation);
}
