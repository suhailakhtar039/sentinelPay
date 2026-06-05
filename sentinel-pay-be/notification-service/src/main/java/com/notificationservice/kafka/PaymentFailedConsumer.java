package com.notificationservice.kafka;

import com.notificationservice.service.NotificationService;
import com.sentinelpay.common.event.PaymentFailedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFailedConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_FAILED,
            groupId = "notification-group"
    )
    public void consume(
            PaymentFailedEvent event) {

        notificationService
                .sendPaymentFailedNotification(
                        event
                );
    }
}