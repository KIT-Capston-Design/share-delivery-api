package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.redis.auth.VerificationType;
import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;

public interface AuthService {
    SMSResponseDTO sendVerificationSMS(String phoneNumber);
    Boolean verifyCode(String phoneNumber, String code, VerificationType verificationType);
}
