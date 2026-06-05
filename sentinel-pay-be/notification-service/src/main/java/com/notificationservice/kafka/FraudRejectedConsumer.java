package com.notificationservice.kafka;

import com.notificationservice.service.NotificationService;
import com.sentinelpay.common.event.FraudRejectedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FraudRejectedConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = KafkaTopics.FRAUD_REJECTED,
            groupId = "notification-group"
    )
    public void consume(
            FraudRejectedEvent event) {

        notificationService
                .sendFraudAlert(
                        event
                );
    }
}