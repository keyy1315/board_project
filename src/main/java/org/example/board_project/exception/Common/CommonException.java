package org.example.board_project.exception.Common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException {
    private final HttpStatus status;

    public CommonException(HttpStatus status, String message) {
        super(message);
        this.status = status;

    }
}
