package com.walletservice.service;

import com.sentinelpay.common.event.FraudApprovedEvent;
import com.walletservice.dto.WalletResponse;

import java.math.BigDecimal;

public interface WalletService {

    void createWalletIfNotExists(Long userId);

    WalletResponse getWalletByUserId(Long userId);

    void debit(Long userId, BigDecimal amount);

    void credit(Long userId, BigDecimal amount);

    void processApprovedPayment(FraudApprovedEvent event);

}
