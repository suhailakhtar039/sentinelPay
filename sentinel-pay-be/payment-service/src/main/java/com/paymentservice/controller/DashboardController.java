package com.paymentservice.controller;

import com.paymentservice.dashboard.DashboardService;
import com.paymentservice.dto.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(service.getDashboard(userId));
    }
}
