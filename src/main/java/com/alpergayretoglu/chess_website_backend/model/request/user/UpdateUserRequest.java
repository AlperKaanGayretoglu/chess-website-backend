package com.alpergayretoglu.chess_website_backend.model.request.user;

import com.alpergayretoglu.chess_website_backend.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UpdateUserRequest {

    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @NotBlank(message = "Email cannot be empty!")
    private String email;

    public static User toEntity(User user, UpdateUserRequest request) {
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        return user;
    }

}
