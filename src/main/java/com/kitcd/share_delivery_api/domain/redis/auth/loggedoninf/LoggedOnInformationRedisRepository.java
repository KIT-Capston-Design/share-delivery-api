package com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf;

import org.springframework.data.repository.CrudRepository;

public interface LoggedOnInformationRedisRepository extends CrudRepository<LoggedOnInformation, String> {
    LoggedOnInformation findLoggedInUserByPhoneNumber(String phoneNumber);
}
