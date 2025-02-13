package org.example.board_project.model.dto.sqlDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
/**
 * searchCode - []
 */
public class searchFilterDTO {
    private String categoryCode;
    private String searchCode;
    private String search;
    private String sortCode;
}
