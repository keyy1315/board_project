package org.example.board_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.board_project.model.Common;

import java.util.List;

@Mapper
public interface CommonMapper {
//      카테고리 리스트 조회 쿼리
    List<Common> getCategory();
    String getCategoryCode(String category);
}
