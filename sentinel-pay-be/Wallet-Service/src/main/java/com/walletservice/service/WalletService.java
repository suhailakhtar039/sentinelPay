package com.walletservice.service;

import com.walletservice.dto.CreateWalletRequest;
import com.walletservice.dto.WalletResponse;

public interface WalletService {
    void createWalletIfNotExists(Long userId);
    WalletResponse getWalletByUserId(Long userId);
}
