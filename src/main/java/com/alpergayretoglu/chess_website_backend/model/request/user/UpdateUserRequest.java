package com.alpergayretoglu.chess_website_backend.model.request.user;

import com.alpergayretoglu.chess_website_backend.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UpdateUserRequest {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Surname cannot be empty!")
    private String surname;

    public static User toEntity(User user, UpdateUserRequest request) {
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        return user;
    }

}
