package org.example.board_project.api;

import lombok.RequiredArgsConstructor;
import org.example.board_project.exception.Board.BoardException;
import org.example.board_project.model.dto.requestDTO.WriteBoardRequestDTO;
import org.example.board_project.model.dto.responseDTO.BoardListResponseDTO;
import org.example.board_project.model.dto.responseDTO.BoardResponseDTO;
import org.example.board_project.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * @param ct_cd     - 카테고리 코드 (전체, 공지, 중요, 일반)
     * @param src_cd    - 검색 조건 (전체, 제목, 내용, 제목+내용, 작성자명)
     * @param search    - 검색어
     * @param sort_cd   - 정렬 조건 (최근 작성일, 조회수)
     * @param page_no   - 현재 페이지 번호 : NOTNULL!!
     * @param page_size - 페이지 당 나타낼 게시글 목록 수 : NOTNULL!!
     * @return BoardListResponseDTO(List < Board > - 게시글 목록, int - 검색총수))
     * 조건에 따른 게시글이 하나도 없을 때는 Exception 처리 없이 List : null, int : 0 을 반환한다.
     */
    @GetMapping("/list")
    public ResponseEntity<Object> getBoardList(
            @RequestParam(required = false) String ct_cd,
            @RequestParam(required = false) String src_cd,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort_cd,
            @RequestParam int page_no,
            @RequestParam int page_size) {
        BoardListResponseDTO boards = boardService.findBoardList(ct_cd, src_cd, search, sort_cd, page_no, page_size);
        return ResponseEntity.ok().body(boards);
    }

}
