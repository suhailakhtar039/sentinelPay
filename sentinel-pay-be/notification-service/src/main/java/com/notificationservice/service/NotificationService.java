package com.notificationservice.service;

import com.sentinelpay.common.event.FraudRejectedEvent;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import com.sentinelpay.common.event.PaymentFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    public void sendPaymentCompletedNotification(
            PaymentCompletedEvent event) {

        log.info(
                "EMAIL SENT: Payment {} completed successfully",
                event.getPaymentId()
        );
    }

    public void sendPaymentFailedNotification(
            PaymentFailedEvent event) {

        log.info(
                "EMAIL SENT: Payment {} failed. Reason={}",
                event.getPaymentId(),
                event.getReason()
        );
    }

    public void sendFraudAlert(
            FraudRejectedEvent event) {

        log.info(
                "EMAIL SENT: Payment {} rejected due to fraud. Reason={}",
                event.getPaymentId(),
                event.getReason()
        );
    }
}
