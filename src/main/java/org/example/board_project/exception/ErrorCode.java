package org.example.board_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
/*
 * 일관적인 예외처리 위해 설정해둠
 */
public enum ErrorCode {
    NONEXISTENT_BOARD(HttpStatus.NOT_FOUND, "NONEXISTENT_REVIEW"),
    FAIL_TO_UPLOAD_BOARD(HttpStatus.SERVICE_UNAVAILABLE, "FAILED UPLOAD BOARD"),
    FAIL_TO_UPDATE_BOARD(HttpStatus.SERVICE_UNAVAILABLE, "FAILED TO UPDATE BOARD"),
    FAIL_TO_DELETE_BOARD(HttpStatus.SERVICE_UNAVAILABLE, "FAILED DELETE BOARD"),
    DTO_OBJECT_IS_EMPTY(HttpStatus.BAD_REQUEST, "DTO OBJECT IS EMPTY"),

    NONEXISTENT_CATEGORY(HttpStatus.NOT_FOUND, "NONEXISTENT_CATEGORY"),

    FAIL_TO_UPLOAD_FILE(HttpStatus.SERVICE_UNAVAILABLE, "FAILED UPLOAD FILE"),
    FAIL_TO_DOWNLOAD_FILE(HttpStatus.SERVICE_UNAVAILABLE, "FAILED DOWNLOAD FILE"),
    FAIL_TO_UPDATE_FILE(HttpStatus.SERVICE_UNAVAILABLE, "FAILED TO UPDATE FILE"),;

    private final HttpStatus httpStatus;
    private final String message;

}
