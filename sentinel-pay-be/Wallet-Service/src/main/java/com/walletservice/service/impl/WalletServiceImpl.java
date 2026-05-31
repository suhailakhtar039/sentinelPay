package com.walletservice.service.impl;

import com.sentinelpay.common.exception.ResourceNotFoundException;
import com.walletservice.dto.WalletResponse;
import com.walletservice.entity.Wallet;
import com.walletservice.enums.WalletStatus;
import com.walletservice.repository.WalletRepository;
import com.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;

    @Override
    @Transactional
    public void createWalletIfNotExists(Long userId) {
        boolean exists = repository.existsByUserId(userId);
        if (exists) {
            log.info("Wallet already exists for user {}", userId);
//            throw new BadRequestException("Wallet already exists for user");
        }
        Wallet wallet = Wallet.builder()
                .userId(userId)
                .balance(BigDecimal.ZERO)
                .currency("INR")
                .status(WalletStatus.ACTIVE)
                .build();
        repository.save(wallet);
        log.info("Wallet created for user id {}", userId);
//        return WalletResponse.builder()
//                .walletId(wallet.getWalletId())
//                .balance(wallet.getBalance())
//                .userId(wallet.getUserId())
//                .currency(wallet.getCurrency())
//                .status(wallet.getStatus().name())
//                .build();
    }

    @Override
    public WalletResponse getWalletByUserId(Long userId) {
        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet Not Found"));

        return WalletResponse.builder()
                .walletId(wallet.getWalletId())
                .userId(wallet.getUserId())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .status(wallet.getStatus().name())
                .build();
    }
}
