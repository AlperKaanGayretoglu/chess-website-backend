package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.request.auth.LoginRequest;
import com.alpergayretoglu.chess_website_backend.model.request.auth.RegisterRequest;
import com.alpergayretoglu.chess_website_backend.model.response.LoginResponse;
import com.alpergayretoglu.chess_website_backend.model.response.UserResponse;
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import com.alpergayretoglu.chess_website_backend.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_EXISTS);
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }

        return LoginResponse.builder()
                .id(user.getId())
                .token(jwtService.createToken(user.getId()))
                .userRole(user.getUserRole())
                .build();
    }

    public Optional<User> getAuthenticatedUser() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            return Optional.empty();
        }
        return userRepository.findById(principal);
    }

}
