package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;

public interface SENSService {
    SMSResponseDTO sendSMS(String recipientPhoneNumber, String content);
}
