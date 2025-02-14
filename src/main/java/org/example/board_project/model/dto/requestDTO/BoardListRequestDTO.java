package org.example.board_project.model.dto.requestDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
/**
 * searchCode - [all, writer_nm, title, cont, tiCont]
 * sortCode - [view_cnt, reg_dt]
 */
public class BoardListRequestDTO {
    private String categoryCode;
    private String searchCode;
    private String search;
    private String sortCode;
    private int offset;
    private int size;
}
