package com.kitcd.share_delivery_api.domain.redis.auth;

import org.springframework.data.repository.CrudRepository;

public interface VerificationSMSRedisRepository extends CrudRepository<VerificationSMS, String> {

}
