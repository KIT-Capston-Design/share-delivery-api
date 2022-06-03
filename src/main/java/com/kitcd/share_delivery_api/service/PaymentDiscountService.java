package com.kitcd.share_delivery_api.service;

import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import com.kitcd.share_delivery_api.dto.paymentdiscount.PaymentDiscountEnrollRequestDTO;

import java.util.List;

public interface PaymentDiscountService {

    List<PaymentDiscount> enrollPaymentDiscount(List<PaymentDiscountEnrollRequestDTO> dto, Payment payment);
}
