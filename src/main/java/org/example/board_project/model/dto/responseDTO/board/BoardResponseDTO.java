package org.example.board_project.model.dto.responseDTO.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * response 객체
 * Board Entity 자체를 넘기지 않게 하기 위해 구현
 * Board Entity 에서 password 값만 제외하고 구현
 */
@AllArgsConstructor
@Getter
@Builder
@RequiredArgsConstructor
public class BoardResponseDTO {
    private int board_no;
    private String category_cd;
    private String title;
    private String cont;
    private String writer_nm;
    private int view_cnt;
    private LocalDateTime reg_dt;
    private LocalDateTime mod_dt;
}
