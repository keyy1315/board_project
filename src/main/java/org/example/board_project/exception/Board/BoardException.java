package org.example.board_project.exception.Board;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
/*
 * custom Exception
 */
public class BoardException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BoardException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }
}
