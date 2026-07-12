package com.walletservice.service.impl;

import com.sentinelpay.common.event.FraudApprovedEvent;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import com.sentinelpay.common.exception.BadRequestException;
import com.sentinelpay.common.exception.ResourceNotFoundException;
import com.sentinelpay.common.exception.WalletNotFoundException;
import com.walletservice.dto.WalletResponse;
import com.walletservice.dto.WalletTopUpRequest;
import com.walletservice.entity.Wallet;
import com.walletservice.enums.WalletStatus;
import com.walletservice.kafka.WalletEventProducer;
import com.walletservice.repository.WalletRepository;
import com.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.walletservice.config.CacheNames.WALLET;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;
    private final WalletEventProducer walletEventProducer;
    private final CacheManager cacheManager;

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
    }

    @Cacheable(cacheNames = WALLET, key = "#userId")
    @Override
    public WalletResponse getWalletByUserId(Long userId) {
        log.info("Fetching wallet from MySQL for user {}", userId);
        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet Not Found"));

        return mapToResponse(wallet);
    }

    @Override
    @Transactional
//    @CacheEvict(cacheNames = WALLET, key="#userId", beforeInvocation = false)
    public void debit(Long userId, BigDecimal amount) {
        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() ->
                        new WalletNotFoundException("Wallet with user id " + userId + " not found in debit"));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Insufficient Balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        evictWalletCache(userId);
    }

    @Override
    @Transactional
//    @CacheEvict(cacheNames = WALLET, key="#userId", beforeInvocation = false)
    public void credit(Long userId, BigDecimal amount) {
        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() ->
                        new WalletNotFoundException("Wallet with user id " + userId + " not found in credit"));

        wallet.setBalance(wallet.getBalance().add(amount));

        evictWalletCache(userId);
    }

    @Transactional
    @Override
    public void processApprovedPayment(FraudApprovedEvent event) {
        debit(event.getSenderUserId(), event.getAmount());
        credit(event.getReceiverUserId(), event.getAmount());

        PaymentCompletedEvent paymentCompletedEvent = PaymentCompletedEvent.builder()
                .paymentId(event.getPaymentId())
                .senderUserId(event.getSenderUserId())
                .receiverUserId(event.getReceiverUserId())
                .amount(event.getAmount())
                .currency("INR")
                .build();

        walletEventProducer.publishPaymentCompleted(paymentCompletedEvent);
    }

    //    @CacheEvict(cacheNames = WALLET, key="#userId", beforeInvocation = false)
    @Override
    @Transactional
    public WalletResponse createTopUp(Long userId, WalletTopUpRequest request) {
        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with userId " + userId + " not present."));

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        Wallet updated = repository.save(wallet);

        evictWalletCache(userId);

        return mapToResponse(updated);
    }

    private WalletResponse mapToResponse(Wallet wallet) {
        return WalletResponse.builder()
                .walletId(wallet.getWalletId())
                .userId(wallet.getUserId())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .status(wallet.getStatus().name())
                .build();
    }

    private void evictWalletCache(Long userId) {
        Cache cache = cacheManager.getCache(WALLET);

        if (cache != null) {
            cache.evict(userId);
            log.info("Evicted wallet cache for user {}", userId);
        }
    }
}
