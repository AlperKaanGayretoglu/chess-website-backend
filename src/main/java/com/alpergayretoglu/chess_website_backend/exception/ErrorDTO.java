package com.alpergayretoglu.chess_website_backend.exception;

import com.alpergayretoglu.chess_website_backend.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ErrorDTO {

    private final ZonedDateTime timestamp;
    private final HttpStatus status;
    private final String message;

    public ErrorDTO(ErrorCode errorCode) {
        this.timestamp = DateUtil.now();
        this.message = errorCode.getMessage();
        this.status = errorCode.getHttpStatus();
    }

    public ErrorCode getErrorCode() {
        return new ErrorCode(this.status, this.message);
    }

}
