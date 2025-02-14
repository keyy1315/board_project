package org.example.board_project.model.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * password 를 front 에 넘기지 않기 위해 정의한 response 객체
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
