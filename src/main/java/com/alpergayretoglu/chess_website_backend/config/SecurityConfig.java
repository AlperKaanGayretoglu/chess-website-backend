package com.alpergayretoglu.chess_website_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Value("${security.jwtSecret}")
    private String jwtSecret;

    public String getJwtSecret() {
        return jwtSecret;
    }

}