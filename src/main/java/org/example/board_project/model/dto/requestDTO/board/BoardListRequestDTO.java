package org.example.board_project.model.dto.requestDTO.board;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
/*
  searchCode - [all, writer_nm, title, cont, tiCont]
  sortCode - [view_cnt, reg_dt]
  category_cd - [전체, 공지, 중요, 일반]
 */
/**
 * 게시글 목록 조회 시 요청 DTO
 */
public class BoardListRequestDTO {
    private String category_cd;
    private String searchCode;
    private String search;
    private String sortCode;
    private int offset;
    private int size;
}
