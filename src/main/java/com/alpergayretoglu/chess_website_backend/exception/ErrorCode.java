package com.alpergayretoglu.chess_website_backend.exception;

import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public class ErrorCode {

    // ------------------------------------------------ GLOBAL ------------------------------------------------
    public static ErrorCode UNKNOWN() {
        return new ErrorCode(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
    }

    public static ErrorCode VALIDATION() {
        return new ErrorCode(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed");
    }

    // ------------------------------------------------ FORBIDDEN ------------------------------------------------
    public static ErrorCode AUTHENTICATED_USER_NOT_FOUND() {
        return new ErrorCode(HttpStatus.FORBIDDEN, "Authenticated user not found");
    }

    public static ErrorCode USER_IS_NOT_ADMIN() {
        return new ErrorCode(HttpStatus.FORBIDDEN, "User is not admin");
    }

    public static ErrorCode USER_IS_NOT_SELF() {
        return new ErrorCode(HttpStatus.FORBIDDEN, "User is not self");
    }

    public static ErrorCode USER_IS_NOT_ADMIN_OR_SELF() {
        return new ErrorCode(HttpStatus.FORBIDDEN, "User is not admin or self");
    }

    public static ErrorCode PASSWORD_MISMATCH() {
        return new ErrorCode(HttpStatus.FORBIDDEN, "Password mismatch");
    }

    public static ErrorCode CREATE_CHAT_WITHOUT_SELF() {
        return new ErrorCode(HttpStatus.FORBIDDEN, "You can't create a chat that you are not a part of");
    }

    public static ErrorCode GET_MESSAGES_WITHOUT_ACCESS() {
        return new ErrorCode(HttpStatus.FORBIDDEN, "You can't get messages from a chat that you are not a part of");
    }

    public static ErrorCode USER_NOT_IN_CHAT() {
        return new ErrorCode(HttpStatus.FORBIDDEN, "You can't send a message to a chat that you are not a part of");
    }

    // ------------------------------------------------ CONFLICT ------------------------------------------------
    public static ErrorCode ACCOUNT_ALREADY_EXISTS() {
        return new ErrorCode(HttpStatus.CONFLICT, "Account already exists");
    }

    // ------------------------------------------------ NOT FOUND ------------------------------------------------
    public static ErrorCode USER_NOT_FOUND() {
        return new ErrorCode(HttpStatus.NOT_FOUND, "User not found");
    }

    public static ErrorCode USER_NOT_FOUND_WITH_USERNAME(String username) {
        return new ErrorCode(HttpStatus.NOT_FOUND, "User not found with username: " + username);
    }

    public static ErrorCode USER_NOT_FOUND_WITH_ID(String userId) {
        return new ErrorCode(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
    }

    public static ErrorCode CHAT_NOT_FOUND() {
        return new ErrorCode(HttpStatus.NOT_FOUND, "Chat not found");
    }

    public static ErrorCode CHAT_NOT_FOUND(String chatId) {
        return new ErrorCode(HttpStatus.NOT_FOUND, "Chat not found with id: " + chatId);
    }


    private final HttpStatus httpStatus;
    private final String message;

    public ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ErrorCode) {
            ErrorCode other = (ErrorCode) obj;
            return httpStatus.equals(other.httpStatus) && message.equals(other.message);
        }
        return false;
    }

}
