package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.paymentorderform.PaymentOrderForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PaymentOrderFormService {

    List<PaymentOrderForm> enrollPaymentOrderForm(List<MultipartFile> orderImages, Payment payment);
}
