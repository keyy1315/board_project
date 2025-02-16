package org.example.board_project.model.dto.responseDTO;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 게시글 목록 조회 response 객체
 */
@Builder
@Getter
public class BoardListResponseDTO {
    private List<BoardResponseDTO> boards;
    private int total;
}
