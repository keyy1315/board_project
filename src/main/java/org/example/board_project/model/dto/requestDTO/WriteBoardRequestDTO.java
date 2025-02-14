package org.example.board_project.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WriteBoardRequestDTO {
    private String writer_nm;
    private String password;
    private String title;
    private String cont;
    private String ct_cd;
    private int boardNo;
}
