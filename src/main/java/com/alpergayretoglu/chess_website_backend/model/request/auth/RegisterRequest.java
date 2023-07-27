package com.alpergayretoglu.chess_website_backend.model.request.auth;

import com.alpergayretoglu.chess_website_backend.constants.ApplicationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Email(message = "Invalid Email!")
    @NotBlank(message = "Email must be filled!")
    private String email;

    @Size(min = ApplicationConstants.PASSWORD_MIN_LENGTH, max = ApplicationConstants.PASSWORD_MAX_LENGTH)
    @NotBlank(message = "Password must be filled!")
    private String password;

}