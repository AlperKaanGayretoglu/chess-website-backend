package com.alpergayretoglu.chess_website_backend.model.request.auth;

import com.alpergayretoglu.chess_website_backend.constants.ApplicationConstants;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class ResetPasswordRequest {

    @NotEmpty(message = "Old password must be filled.")
    @Length(min = ApplicationConstants.PASSWORD_MIN_LENGTH)
    private String oldPassword;

    @NotEmpty(message = "New Password must be filled.")
    @Length(min = ApplicationConstants.PASSWORD_MIN_LENGTH)
    private String newPassword;
}
