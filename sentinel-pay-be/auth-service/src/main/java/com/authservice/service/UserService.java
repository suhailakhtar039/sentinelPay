package com.authservice.service;

import com.authservice.dto.ChangePasswordRequest;
import com.authservice.dto.UpdateProfileRequest;
import com.authservice.dto.UserProfileResponse;
import com.authservice.entity.User;
import com.authservice.repository.UserRepo;
import com.sentinelpay.common.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserProfileResponse getMyProfile(Long userId) {
        User user = getUser(userId);
        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public void updateProfile(Long userId, UpdateProfileRequest request) {
        User user = getUser(userId);
        user.setName(request.getName().trim());
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = getUser(userId);
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }
        if (request.getCurrentPassword()
                .equals(request.getNewPassword())) {

            throw new IllegalArgumentException(
                    "New password must be different.");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepo.save(user);
    }

    // helper method
    private User getUser(Long id) {

        return userRepo
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFound(
                                "User not found with id " + id));

    }
}
