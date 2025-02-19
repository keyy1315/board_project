package org.example.board_project.model.dto.requestDTO.board;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Builder
@AllArgsConstructor
@Getter
@ToString
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

    public static BoardListRequestDTO create(String category_cd, String searchCode, String search, String sortCode, int offset, int size) {
        if(!StringUtils.hasText(category_cd)) category_cd = null;
        if(!StringUtils.hasText(searchCode)) searchCode = null;
        if(!StringUtils.hasText(search)) search = null;
        if(!StringUtils.hasText(sortCode)) sortCode = null;

        return BoardListRequestDTO.builder()
                .category_cd(category_cd)
                .searchCode(searchCode)
                .search(search)
                .sortCode(sortCode)
                .offset(offset)
                .size(size)
                .build();
    }
}
