package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.paymentorderform.PaymentOrderFormRepository;
import com.kitcd.share_delivery_api.service.ImageFileService;
import com.kitcd.share_delivery_api.service.PaymentOrderFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional

public class PaymentOrderFormServiceImpl implements PaymentOrderFormService {

    private final PaymentOrderFormRepository paymentOrderFormRepository;
    private final ImageFileService imageFileService;

    @Override
    public List<PaymentOrderFormService> enrollPaymentOrderForm(List<MultipartFile> orderImages, Payment payment) {
        return null;
    }
}
