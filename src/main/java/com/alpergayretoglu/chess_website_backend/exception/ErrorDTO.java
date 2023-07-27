package com.alpergayretoglu.chess_website_backend.exception;

import com.alpergayretoglu.chess_website_backend.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDTO {

    private final ZonedDateTime timestamp;
    private final HttpStatus status;
    private final String message;

    public ErrorDTO(ErrorCode errorCode) {
        this.timestamp = DateUtil.now();
        this.status = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
