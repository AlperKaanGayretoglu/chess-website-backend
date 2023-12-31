package com.alpergayretoglu.chess_website_backend.model.response;

import com.alpergayretoglu.chess_website_backend.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String id;
    private String username;
    private UserRole userRole;
    private String token;
}
