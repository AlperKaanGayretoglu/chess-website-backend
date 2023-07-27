package com.alpergayretoglu.chess_website_backend.security;

public class SecurityConstants {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "ChessWebsite";
    public static final String TOKEN_AUDIENCE = "ChessWebsite";

    private SecurityConstants() {
    }

}
