package com.kitcd.share_delivery_api.domain.redis.auth.verificationemail;

import org.springframework.data.repository.CrudRepository;

public interface VerificationEmailRedisRepository extends CrudRepository<VerificationEmail, String> {
    VerificationEmail findVerificationEmailByEmail(String id);
}
