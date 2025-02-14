package org.example.board_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * MyBatis 반환 객체 타입 정의
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Board {
    private int board_no;
    private String category_cd;
    private String title;
    private String cont;
    private String writer_nm;
    private String password;
    private int view_cnt;
    private LocalDateTime reg_dt;
    private LocalDateTime mod_dt;
}
