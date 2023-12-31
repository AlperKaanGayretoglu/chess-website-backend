package com.alpergayretoglu.chess_website_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
@AllArgsConstructor
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

    // ------------------------------------------------ CONFLICT ------------------------------------------------
    public static ErrorCode ACCOUNT_ALREADY_EXISTS() {
        return new ErrorCode(HttpStatus.CONFLICT, "Account already exists");
    }

    public static ErrorCode USER_CANNOT_START_GAME_NOT_PART_OF() {
        return new ErrorCode(HttpStatus.CONFLICT, "You can't start a game that you are not a part of");
    }

    public static ErrorCode OPPONENTS_CANNOT_BE_SAME() {
        return new ErrorCode(HttpStatus.CONFLICT, "Opponents cannot be the same");
    }

    public static ErrorCode ILLEGAL_MOVE() {
        return new ErrorCode(HttpStatus.CONFLICT, "Illegal move");
    }

    public static ErrorCode CANT_PLAY_MOVE_GAME_HAS_ENDED() {
        return new ErrorCode(HttpStatus.CONFLICT, "Can't play move, game has ended");
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

    public static ErrorCode GAME_NOT_FOUND_WITH_ID(String gameId) {
        return new ErrorCode(HttpStatus.NOT_FOUND, "Game not found with id: " + gameId);
    }

    public static ErrorCode CHESS_MOVE_NOT_FOUND_WITH_ID(String chessMoveId) {
        return new ErrorCode(HttpStatus.NOT_FOUND, "Chess move not found with id: " + chessMoveId);
    }

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ErrorCode) {
            ErrorCode other = (ErrorCode) obj;
            return httpStatus.equals(other.httpStatus) && message.equals(other.message);
        }
        return false;
    }

}
