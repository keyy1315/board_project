package org.example.board_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.board_project.model.dto.requestDTO.board.BoardListRequestDTO;
import org.example.board_project.model.dto.requestDTO.board.WriteBoardRequestDTO;
import org.example.board_project.model.Board;

import java.util.List;

@Mapper
public interface BoardMapper {
//      검색 필터로 게시글 총 개수 조회 쿼리
    int countBoard(BoardListRequestDTO sqlDTO);
//      검색 필터로 게시글 리스트 조회 쿼리
    List<Board> findWithFilter(BoardListRequestDTO sqlDTO);
//      게시글 저장 쿼리
    int saveBoard(WriteBoardRequestDTO dto);
//      게시글 pk로 게시글 조회 쿼리
    Board findBoard(int no);
//      게시글 수정 쿼리
    int updateBoard(WriteBoardRequestDTO dto);
//      조회수 증가 쿼리
    int IncrementViewCnt(int no);
//      게시글 삭제 쿼리
    int deleteBoard(int no);
//      게시글 비밀번호 조회 쿼리 - 수정, 삭제
    String getBoardPassword(int no);

}
