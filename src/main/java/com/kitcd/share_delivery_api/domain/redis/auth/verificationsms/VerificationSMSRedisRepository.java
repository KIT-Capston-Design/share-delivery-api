package com.kitcd.share_delivery_api.domain.redis.auth.verificationsms;

import org.springframework.data.repository.CrudRepository;

public interface VerificationSMSRedisRepository extends CrudRepository<VerificationSMS, String> {
    VerificationSMS findVerificationSMSByPhoneNumber(String phoneNumber);
}
