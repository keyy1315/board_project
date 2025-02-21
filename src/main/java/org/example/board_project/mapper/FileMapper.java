package org.example.board_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.board_project.model.BoardFile;
import org.example.board_project.model.dto.responseDTO.file.FileResponseDTO;

import java.util.List;

@Mapper
public interface FileMapper {
//      첨부한 파일 저장 쿼리
    int saveFile(BoardFile boardFile);
//      파일 pk로 저장된 파일 조회 쿼리
    BoardFile getFile(int fileNo);
//      게시글 pk로 저장된 파일 리스트 조회 쿼리
    List<FileResponseDTO> getFilesByBoardNo(int boardNo);
//      파일 pk로 파일 삭제 쿼리
    int deleteFiles(Integer i);
}
