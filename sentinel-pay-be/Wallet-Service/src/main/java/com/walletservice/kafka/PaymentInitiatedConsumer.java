package com.walletservice.kafka;

import com.sentinelpay.common.event.PaymentInitiatedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import com.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentInitiatedConsumer {

    private final WalletService walletService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_INITIATED, groupId = "wallet-group")
    public void consume(PaymentInitiatedEvent event) {
        log.info("Received payment event:{}", event.getPaymentId());
        // Debit service
        walletService.debit(event.getSenderUserId(), event.getAmount());

        // Credit service
        walletService.credit(event.getReceiverUserId(), event.getAmount());
    }

}
