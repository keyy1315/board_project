package org.example.board_project.service;

import lombok.RequiredArgsConstructor;
import org.example.board_project.mapper.BoardMapper;
import org.example.board_project.model.dto.responseDTO.BoardListResponseDTO;
import org.example.board_project.model.dto.sqlDTO.searchFilterDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    /**
     * @param categoryCode - 카테고리 코드
     * @param searchCode - 검색 조건
     * @param search - 검색어
     * @param sortCode - 정렬조건
     * @return BoardListResponseDTO
     */
    public BoardListResponseDTO findBoardList(
            String categoryCode,
            String searchCode,
            String search,
            String sortCode) {
        searchFilterDTO sqlDTO = new searchFilterDTO(categoryCode, searchCode, search, sortCode);

        return BoardListResponseDTO.builder()
                .board(boardMapper.findWithFilter(sqlDTO))
                .total(boardMapper.countBoard(sqlDTO))
                .build();
    }
}
