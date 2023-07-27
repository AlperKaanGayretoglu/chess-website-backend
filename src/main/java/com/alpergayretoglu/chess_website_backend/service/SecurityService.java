package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.exception.BusinessException;
import com.alpergayretoglu.chess_website_backend.exception.ErrorCode;
import com.alpergayretoglu.chess_website_backend.model.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SecurityService {

    public User assertUser(Optional<User> authenticatedUser) {
        return authenticatedUser.orElseThrow(() -> new BusinessException(ErrorCode.AUTHENTICATED_USER_NOT_FOUND));
    }

    public User assertAdmin(Optional<User> authenticatedUser) {
        User user = assertUser(authenticatedUser);
        if (!user.getUserRole().equals(UserRole.ADMIN)) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_ADMIN);
        }
        return user;
    }

    public User assertSelf(Optional<User> authenticatedUser, String userId) {
        User user = assertUser(authenticatedUser);
        if (!user.getId().equals(userId)) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_SELF);
        }
        return user;
    }

    public User assertAdminOrSelf(Optional<User> authenticatedUser, String userId) {
        User user = assertUser(authenticatedUser);
        if (!user.getUserRole().equals(UserRole.ADMIN) && !user.getId().equals(userId)) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_ADMIN_OR_SELF);
        }
        return user;
    }

}
