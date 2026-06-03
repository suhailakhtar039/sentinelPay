package com.fraudservice.service;

import com.sentinelpay.common.event.PaymentInitiatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionService {

    public boolean isFraudulent(PaymentInitiatedEvent event){
        return event.getAmount().compareTo(BigDecimal.valueOf(10000))>0;
    }

}
