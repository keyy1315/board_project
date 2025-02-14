package org.example.board_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NONEXISTENT_BOARD(HttpStatus.NOT_FOUND, "NONEXISTENT_REVIEW"),
    FAIL_TO_UPLOAD_BOARD(HttpStatus.SERVICE_UNAVAILABLE, "FAILED UPLOAD BOARD"),
    FAIL_TO_UPDATE_BOARD(HttpStatus.SERVICE_UNAVAILABLE, "FAILED TO UPDATE BOARD"),
    FAIL_TO_DELETE_BOARD(HttpStatus.SERVICE_UNAVAILABLE, "FAILED DELETE BOARD"),
    DTO_OBJECT_IS_EMPTY(HttpStatus.BAD_REQUEST, "DTO OBJECT IS EMPTY"),
    NO_AUTHORITY(HttpStatus.UNAUTHORIZED, "NO AUTHORITY"),;

    private final HttpStatus httpStatus;
    private final String message;

}
