package com.authservice.controller;

import com.authservice.dto.RegisterRequest;
import com.authservice.dto.RegisterResponse;
import com.authservice.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService service;

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> registerNewUser(@RequestBody RegisterRequest request){
        return new ResponseEntity<>(service.register(request), HttpStatus.OK);
    }
}
