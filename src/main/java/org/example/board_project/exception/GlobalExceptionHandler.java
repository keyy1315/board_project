package org.example.board_project.exception;

import org.example.board_project.exception.Board.BoardException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 비즈니스 로직에서 생긴 BoardException 처리
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BoardException.class)
    public ResponseEntity<Map<String, Object>> handleException(BoardException e){
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", e.getMessage());
        resp.put("status", e.getHttpStatus());
        return new ResponseEntity<>(resp, e.getHttpStatus());
//        boardException 발생 시 설정해둔 msg, status 반환
    }
}
