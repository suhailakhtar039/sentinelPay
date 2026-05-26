package com.walletservice.controller;

import com.walletservice.dto.CreateWalletRequest;
import com.walletservice.dto.WalletResponse;
import com.walletservice.response.ApiResponse;
import com.walletservice.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService service;

    @PostMapping
    public ResponseEntity<ApiResponse<WalletResponse>> createWallet(
            @Valid @RequestBody CreateWalletRequest request
    ) {
        WalletResponse response = service.createWallet(request);
        return ResponseEntity.ok(
                ApiResponse.<WalletResponse>builder()
                        .success(true)
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<WalletResponse>> getWallet(@PathVariable Long userId) {
        WalletResponse response = service.getWalletByUserId(userId);

        return ResponseEntity.ok(
                ApiResponse.<WalletResponse>builder()
                        .success(true)
                        .message("Wallet fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
