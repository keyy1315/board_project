package org.example.board_project.model.dto.responseDTO;

import lombok.Builder;
import lombok.Getter;
import org.example.board_project.model.entity.Board;

import java.util.List;

@Builder
@Getter
public class BoardListResponseDTO {
    private List<Board> board;
    private int total;
}
