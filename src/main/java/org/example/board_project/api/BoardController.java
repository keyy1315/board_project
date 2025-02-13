package org.example.board_project.api;

import lombok.RequiredArgsConstructor;
import org.example.board_project.model.dto.responseDTO.BoardListResponseDTO;
import org.example.board_project.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * @param ct_cd - 카테고리 코드 (전체, 공지, 중요, 일반)
     * @param src_cd - 검색 조건 (전체, 제목, 내용, 제목+내용, 작성자명)
     * @param search - 검색어
     * @param sort_cd - 정렬 조건 (최근 작성일, 조회수)
     * @return BoardListResponseDTO(List<Board> - 게시판 목록, int - 검색총수))
     */
    @GetMapping("/list")
    public ResponseEntity<Object> getBoardList(
            @RequestParam(required = false) String ct_cd,
            @RequestParam(required = false) String src_cd,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort_cd) {
        BoardListResponseDTO boards = boardService.findBoardList(ct_cd, src_cd, search, sort_cd);
        return ResponseEntity.ok().body(boards);
    }

}
