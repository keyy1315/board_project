package org.example.board_project.model.dto.responseDTO.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 게시글 목록 조회 response 객체
 */
@Builder
@Getter
@ToString
public class BoardListResponseDTO {
    private List<BoardResponseDTO> boards;
    private int total;
}
