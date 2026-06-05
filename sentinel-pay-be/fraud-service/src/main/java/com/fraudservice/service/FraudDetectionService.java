package com.fraudservice.service;

import com.fraudservice.dto.FraudDecision;
import com.sentinelpay.common.event.PaymentInitiatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionService {

    public FraudDecision evaluate(PaymentInitiatedEvent event) {
        if (event.getAmount().compareTo(BigDecimal.valueOf(10000)) > 0) {
            return FraudDecision.builder()
                    .fraudulent(true)
                    .reason("HIGH_VALUE_TRANSACTION")
                    .build();
        } else {
            return FraudDecision.builder()
                    .fraudulent(false)
                    .reason("APPROVED")
                    .build();
        }
    }

}
