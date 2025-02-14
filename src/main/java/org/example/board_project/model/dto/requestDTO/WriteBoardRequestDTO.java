package org.example.board_project.model.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 글 작성/수정 요청할 때 전송하는 데이터 객체
 */
@AllArgsConstructor
@Getter
public class WriteBoardRequestDTO {
    private String writer_nm;
    private String password;
    private String title;
    private String cont;
    private String category_cd;
    private int boardNo;
}
