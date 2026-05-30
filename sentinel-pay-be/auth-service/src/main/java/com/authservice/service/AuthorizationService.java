package com.authservice.service;

import com.authservice.client.WalletClient;
import com.authservice.dto.LoginRequest;
import com.authservice.dto.LoginResponse;
import com.authservice.dto.RegisterRequest;
import com.authservice.dto.RegisterResponse;
import com.authservice.dto.RoleEnum;
import com.authservice.entity.User;
import com.authservice.kafka.UserEventProducer;
import com.authservice.repository.UserRepo;
import com.sentinelpay.common.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final WalletClient walletClient;
    private final UserEventProducer userEventProducer;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleEnum.ROLE_USER);
        User savedUser = userRepo.save(user);

        String correlationId = UUID.randomUUID().toString();
        UserRegisteredEvent event = UserRegisteredEvent
                .builder()
                .correlationID(correlationId)
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .build();
//        walletClient.createWallet(savedUser.getId());
        userEventProducer.publishUserRegisteredEvent(event);
        response.setMessage("User registered successfully");
        return response;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepo
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User with email " + email + " is not found"));
        boolean passwordMatch =
                passwordEncoder.matches(password, user.getPassword());

        if (!passwordMatch) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(email);
        loginResponse.setToken(token);
        return loginResponse;
    }
}
