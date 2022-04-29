package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;

public interface AuthService {
    SMSResponseDTO sendVerificationSMS(String phoneNumber);
}
