package com.kitcd.share_delivery_api.service.impl;

import com.kitcd.share_delivery_api.domain.jpa.payment.Payment;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscount;
import com.kitcd.share_delivery_api.domain.jpa.paymentdiscount.PaymentDiscountRepository;
import com.kitcd.share_delivery_api.dto.paymentdiscount.PaymentDiscountEnrollRequestDTO;
import com.kitcd.share_delivery_api.service.PaymentDiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PaymentDiscountServiceImpl implements PaymentDiscountService {

    private final PaymentDiscountRepository paymentDiscountRepository;

    @Override
    public List<PaymentDiscount> enrollPaymentDiscount(List<PaymentDiscountEnrollRequestDTO> dto, Payment payment) {
        return dto.stream().map(i -> paymentDiscountRepository.save(i.toEntity(payment))).collect(Collectors.toList());
    }
}
