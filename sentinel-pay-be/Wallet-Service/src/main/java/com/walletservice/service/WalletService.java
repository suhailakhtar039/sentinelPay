package com.walletservice.service;

import com.walletservice.dto.CreateWalletRequest;
import com.walletservice.dto.WalletResponse;

public interface WalletService {
    WalletResponse createWallet(CreateWalletRequest request);
    WalletResponse getWalletByUserId(Long userId);
}
