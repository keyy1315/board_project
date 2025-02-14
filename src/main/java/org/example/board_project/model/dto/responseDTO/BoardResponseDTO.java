package org.example.board_project.model.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

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
