package com.ledgerservice.controller;

import com.ledgerservice.dto.LedgerResponse;
import com.ledgerservice.service.LedgerService;
import com.sentinelpay.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    @GetMapping("/me")
    public ResponseEntity<
            ApiResponse<List<LedgerResponse>>
            > getMyTransactions(
            @RequestHeader("X-User-Id") Long userId
    ) {

        List<LedgerResponse> response =
                ledgerService.getMyTransactions(userId);

        return ResponseEntity.ok(
                ApiResponse.<List<LedgerResponse>>builder()
                        .success(true)
                        .message("Transactions retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}