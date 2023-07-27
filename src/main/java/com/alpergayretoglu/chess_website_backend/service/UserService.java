package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.request.auth.ResetPasswordRequest;
import com.alpergayretoglu.chess_website_backend.model.request.user.CreateUserRequest;
import com.alpergayretoglu.chess_website_backend.model.request.user.UpdateUserRequest;
import com.alpergayretoglu.chess_website_backend.model.response.UserResponse;
import com.alpergayretoglu.chess_website_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final SecurityService securityService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public Page<UserResponse> listUsers(Optional<User> authenticatedUserOptional, Pageable pageable) {
        securityService.assertAdmin(authenticatedUserOptional);

        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserResponse::fromEntity);
    }

    public UserResponse getUser(Optional<User> authenticatedUserOptional, String userId) {
        securityService.assertUser(authenticatedUserOptional);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );
        return UserResponse.fromEntity(user);
    }

    public UserResponse createUser(Optional<User> authenticatedUserOptional, CreateUserRequest createUserRequest) {
        securityService.assertAdmin(authenticatedUserOptional);

        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_EXISTS);
        }

        User newUser = userRepository.save(CreateUserRequest.toEntity(createUserRequest));
        return UserResponse.fromEntity(newUser);
    }

    public UserResponse updateUser(Optional<User> authenticatedUserOptional, String userId, UpdateUserRequest updateUserRequest) {
        securityService.assertAdminOrSelf(authenticatedUserOptional, userId);

        User oldUser = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );
        User newUser = userRepository.save(UpdateUserRequest.toEntity(oldUser, updateUserRequest));
        return UserResponse.fromEntity(newUser);
    }

    public UserResponse deleteUser(Optional<User> authenticatedUserOptional, String userId) {
        securityService.assertAdmin(authenticatedUserOptional);

        User oldUser = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );
        userRepository.delete(oldUser);
        return UserResponse.fromEntity(oldUser);
    }

    public UserResponse resetPassword(Optional<User> authenticatedUser, ResetPasswordRequest resetPasswordRequest) {
        User user = securityService.assertUser(authenticatedUser);

        if (!passwordEncoder.matches(resetPasswordRequest.getOldPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }

        user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));

        User newUser = userRepository.save(user);
        return UserResponse.fromEntity(newUser);
    }

}
