package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.redis.auth.loggedoninf.LoggedOnInformation;
import com.kitcd.share_delivery_api.domain.redis.auth.verificationsms.VerificationType;
import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;

public interface AuthService {

    void saveLoggedOnInformation(LoggedOnInformation loggedOnInformation);
    SMSResponseDTO sendVerificationSMS(String phoneNumber);
    Boolean verifyCode(String phoneNumber, String code, VerificationType verificationType);
}
