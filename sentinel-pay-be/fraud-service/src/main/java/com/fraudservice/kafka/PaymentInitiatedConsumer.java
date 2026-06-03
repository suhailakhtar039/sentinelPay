package com.fraudservice.kafka;

import com.fraudservice.service.FraudDetectionService;
import com.sentinelpay.common.event.PaymentInitiatedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentInitiatedConsumer {
    private final FraudDetectionService fraudDetectionService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_INITIATED, groupId = "fraud-service")
    public void consume(PaymentInitiatedEvent event) {
        boolean fraudulent = fraudDetectionService.isFraudulent(event);
        if (fraudulent)
            log.info("Fraud occurred");
        else log.info("Fraud not occurred");
    }

}
