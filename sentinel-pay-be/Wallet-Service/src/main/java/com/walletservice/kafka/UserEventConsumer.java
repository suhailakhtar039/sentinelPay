package com.walletservice.kafka;

import com.sentinelpay.common.event.UserRegisteredEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import com.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEventConsumer {
    private final WalletService walletService;

    @KafkaListener(topics = KafkaTopics.USER_REGISTERED, groupId = "wallet-group")
    public void consume(UserRegisteredEvent event) {
        log.info("Received USER_REGISTERED event for userId:{}", event.getUserId());
//        CreateWalletRequest request = CreateWalletRequest.builder()
//                .userId(event.getUserId())
//                .build();

        walletService.createWalletIfNotExists(event.getUserId());
    }

}
