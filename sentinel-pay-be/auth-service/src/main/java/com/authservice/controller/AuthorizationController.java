package com.authservice.controller;

import com.authservice.dto.LoginRequest;
import com.authservice.dto.LoginResponse;
import com.authservice.dto.RegisterRequest;
import com.authservice.dto.RegisterResponse;
import com.authservice.service.AuthorizationService;
import com.sentinelpay.common.dto.TokenValidationRequest;
import com.sentinelpay.common.dto.TokenValidationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService service;

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> registerNewUser(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(service.register(request), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(service.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("validate")
    public ResponseEntity<TokenValidationResponse> validate(
            @RequestBody TokenValidationRequest request
    ) {
        return new ResponseEntity<>(service.validateToken(request.getToken()),
                HttpStatus.OK);
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authHeader
    ) {
        service.logout(authHeader);
        return ResponseEntity.ok().build();
    }
}
