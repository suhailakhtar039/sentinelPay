package com.walletservice.controller;

import com.sentinelpay.common.response.ApiResponse;
import com.walletservice.dto.CreateWalletRequest;
import com.walletservice.dto.WalletResponse;
import com.walletservice.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService service;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createWallet(
            @Valid @RequestBody CreateWalletRequest request
    ) {
        service.createWalletIfNotExists(request.getUserId());
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .data("Wallet Created Successfully!")
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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<WalletResponse>> getMyWallet(
            @RequestHeader("X-User-Id") Long userId){
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

    @GetMapping("/test")
    public String test(
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Role") String role
    ) {
        return userId + " | " + email + " | " + role;
    }

}
