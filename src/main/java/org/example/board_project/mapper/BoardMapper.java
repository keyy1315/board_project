package org.example.board_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.board_project.model.dto.requestDTO.board.BoardListRequestDTO;
import org.example.board_project.model.dto.requestDTO.board.WriteBoardRequestDTO;
import org.example.board_project.model.Board;

import java.util.List;

@Mapper
public interface BoardMapper {
    int countBoard(BoardListRequestDTO sqlDTO);

    List<Board> findWithFilter(BoardListRequestDTO sqlDTO);

    int saveBoard(WriteBoardRequestDTO dto);

    Board findBoard(int no);

    int updateBoard(WriteBoardRequestDTO dto);

    int IncrementViewCnt(int no);

    int deleteBoard(int no);

    String getBoardPassword(int no);
}
