package com.alpergayretoglu.chess_website_backend.exception;

import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public class ErrorCode {
    public static final ErrorCode UNKNOWN = new ErrorCode(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
    public static final ErrorCode VALIDATION = new ErrorCode(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed");

    public static final ErrorCode AUTHENTICATED_USER_NOT_FOUND = new ErrorCode(HttpStatus.FORBIDDEN, "Authenticated user not found");
    public static final ErrorCode USER_IS_NOT_ADMIN = new ErrorCode(HttpStatus.FORBIDDEN, "User is not admin");
    public static final ErrorCode USER_IS_NOT_SELF = new ErrorCode(HttpStatus.FORBIDDEN, "User is not self");
    public static final ErrorCode USER_IS_NOT_ADMIN_OR_SELF = new ErrorCode(HttpStatus.FORBIDDEN, "User is not admin or self");
    public static final ErrorCode PASSWORD_MISMATCH = new ErrorCode(HttpStatus.FORBIDDEN, "Password mismatch");

    public static final ErrorCode CREATE_CHAT_WITHOUT_SELF = new ErrorCode(HttpStatus.FORBIDDEN, "You can't create a chat that you are not a part of");
    public static final ErrorCode GET_MESSAGES_WITHOUT_ACCESS = new ErrorCode(HttpStatus.FORBIDDEN, "You can't get messages from a chat that you are not a part of");
    public static final ErrorCode USER_NOT_IN_CHAT = new ErrorCode(HttpStatus.FORBIDDEN, "You can't send a message to a chat that you are not a part of");


    public static final ErrorCode ACCOUNT_ALREADY_EXISTS = new ErrorCode(HttpStatus.CONFLICT, "Account already exists");

    public static final ErrorCode USER_NOT_FOUND = new ErrorCode(HttpStatus.NOT_FOUND, "User not found");
    public static final ErrorCode CHAT_NOT_FOUND = new ErrorCode(HttpStatus.NOT_FOUND, "Chat not found");

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
