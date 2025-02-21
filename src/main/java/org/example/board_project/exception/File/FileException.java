package org.example.board_project.exception.File;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileException extends RuntimeException{
    private final HttpStatus status;

    public FileException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
