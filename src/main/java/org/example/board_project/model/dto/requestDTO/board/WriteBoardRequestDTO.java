package org.example.board_project.model.dto.requestDTO.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.board_project.model.dto.requestDTO.file.FileRequestDTO;
import org.example.board_project.model.entity.Board;

/**
 * 글 작성/수정 요청할 때 전송하는 데이터 객체
 */
@AllArgsConstructor
@Getter
@Builder
public class WriteBoardRequestDTO {
    private String writer_nm;
    private String password;
    private String title;
    private String cont;
    private String category_cd;
    private int boardNo;
    private FileRequestDTO file;

    public static WriteBoardRequestDTO from(int boardNo, WriteBoardRequestDTO dto) {
        return WriteBoardRequestDTO.builder()
                .boardNo(boardNo)
                .writer_nm(dto.getWriter_nm())
                .password(dto.getPassword())
                .title(dto.getTitle())
                .cont(dto.getCont())
                .category_cd(dto.getCategory_cd())
                .file(dto.getFile())
                .build();
    }
}
