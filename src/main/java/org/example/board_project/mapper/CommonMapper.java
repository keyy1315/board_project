package org.example.board_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.board_project.model.Common;

import java.util.List;

@Mapper
public interface CommonMapper {
    List<Common> getCategory();
}
