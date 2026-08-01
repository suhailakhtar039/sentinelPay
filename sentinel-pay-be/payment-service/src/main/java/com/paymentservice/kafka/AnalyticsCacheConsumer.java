package com.paymentservice.kafka;

import com.paymentservice.cache.AnalyticsCacheService;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsCacheConsumer {
    private final AnalyticsCacheService analyticsCacheService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_COMPLETED, groupId = "payment-analytics-cache")
    public void consume(PaymentCompletedEvent event) {
        log.info("Evicting analytics cache for payment {}", event.getPaymentId());

        analyticsCacheService.evictUserAnalytics(event.getSenderUserId());

        analyticsCacheService.evictUserAnalytics(event.getReceiverUserId());
    }
}
