package com.alpergayretoglu.chess_website_backend.model.request.user;

import com.alpergayretoglu.chess_website_backend.constants.ApplicationConstants;
import com.alpergayretoglu.chess_website_backend.entity.User;
import com.alpergayretoglu.chess_website_backend.model.enums.UserRole;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class CreateUserRequest {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @Email(message = "Invalid email address!")
    @NotBlank(message = "Mail address cannot be empty!")
    private String email;

    @NotBlank
    @Size(min = ApplicationConstants.PASSWORD_MIN_LENGTH, max = ApplicationConstants.PASSWORD_MAX_LENGTH)
    private String password;

    public static User toEntity(CreateUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.GUEST)
                .build();
    }

}
