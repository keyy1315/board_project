package org.example.board_project.model.dto.requestDTO.board;

import lombok.*;

/**
 * 글 작성/수정 요청할 때 전송하는 데이터 객체
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class WriteBoardRequestDTO {
    private String writer_nm;
    private String password;
    private String title;
    private String cont;

    @Setter
    private String category_cd;

    private int boardNo;

//    게시글 수정 시 게시글 번호와 새로운 내용 객체 생성을 위한 of 메소드
    public static WriteBoardRequestDTO of(int boardNo, WriteBoardRequestDTO dto, String category) {
        return WriteBoardRequestDTO.builder()
                .boardNo(boardNo)
                .writer_nm(dto.getWriter_nm())
                .password(dto.getPassword())
                .title(dto.getTitle())
                .cont(dto.getCont())
                .category_cd(category)
                .build();
    }
}
