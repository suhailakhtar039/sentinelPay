package com.authservice.service;

import com.authservice.dto.RegisterRequest;
import com.authservice.dto.RegisterResponse;
import com.authservice.dto.RoleEnum;
import com.authservice.entity.User;
import com.authservice.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request){
        RegisterResponse response = new RegisterResponse();

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleEnum.ROLE_USER);
        userRepo.save(user);
        response.setMessage("User registered successfully");
        return response;
    }
}
