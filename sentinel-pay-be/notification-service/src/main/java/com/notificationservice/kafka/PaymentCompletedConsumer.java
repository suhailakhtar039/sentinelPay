package com.notificationservice.kafka;

import com.notificationservice.service.NotificationService;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCompletedConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_COMPLETED,
            groupId = "notification-group"
    )
    public void consume(
            PaymentCompletedEvent event) {

        notificationService
                .sendPaymentCompletedNotification(
                        event
                );
    }
}