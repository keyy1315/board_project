package org.example.board_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.board_project.model.dto.requestDTO.BoardListRequestDTO;
import org.example.board_project.model.dto.requestDTO.WriteBoardRequestDTO;
import org.example.board_project.model.entity.Board;

import java.util.List;

@Mapper
public interface BoardMapper {
    int countBoard(BoardListRequestDTO sqlDTO);

    List<Board> findWithFilter(BoardListRequestDTO sqlDTO);

    int countBoard(searchFilterDTO sqlDTO);

    List<Board> findWithFilter(searchFilterDTO sqlDTO);
}
