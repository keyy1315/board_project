package org.example.board_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.board_project.model.dto.sqlDTO.searchFilterDTO;
import org.example.board_project.model.entity.Board;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> findAll();

    int countBoard(searchFilterDTO sqlDTO);

    List<Board> findWithFilter(searchFilterDTO sqlDTO);
}
