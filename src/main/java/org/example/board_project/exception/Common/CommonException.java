package org.example.board_project.exception.Common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
public class CommonException extends RuntimeException{
    private final HttpStatus httpStatus;

    public CommonException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }
}
