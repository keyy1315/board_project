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

    /**
     * @param no - 게시글 번호
     * @return Board
     * @throws BoardException (상태코드 : 404 Not Found)
     * 게시글 조회 : board_no(게시글 번호)를 통해 게시글 조회수 증가 후 게시글 객체 반환
     * 번호에 맞는 게시글이 존재하지 않을 때 404 Error 반환
     */
    @GetMapping("/no")
    public ResponseEntity<Object> getBoard(@RequestParam int no) {
        BoardResponseDTO board = boardService.findBoard(no);
        return ResponseEntity.ok().body(board);
    }

    /**
     * @param writer_nm - 작성자명
     * @param password  - 비밀번호
     * @param ct_cd     - 카테고리 코드
     * @param title     - 제목
     * @param cont      - 내용
     * @return Board - 작성된 게시글 객체
     * @throws BoardException (상태코드 : 503 Service Unavailable)
     * 게시글 작성 : 게시글 작성 후 게시글 객체 반환
     *
     */
    @PostMapping("/write")
    public ResponseEntity<Object> writeBoard(
            @RequestParam String writer_nm,
            @RequestParam String password,
            @RequestParam String ct_cd,
            @RequestParam String title,
            @RequestParam String cont) {
        WriteBoardRequestDTO dto = new WriteBoardRequestDTO(writer_nm, password, title, cont, ct_cd, 0);
        BoardResponseDTO board = boardService.saveBoard(dto);
        return ResponseEntity.ok().body(board);
    }


    /**
     * @param no - 게시글 번호
     * @param writer_nm - 작성자명
     * @param password - 비밀번호
     * @param ct_cd - 카테고리 코드
     * @param title - 제목
     * @param cont - 내용
     * @return Board
     * @throws BoardException (상태코드 : 503 Service Unavailable)
     * 게시글 수정 : 게시글 수정 후 게시글 객체 반환
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateBoard(
            @RequestParam int no,
            @RequestParam String writer_nm,
            @RequestParam String password,
            @RequestParam String ct_cd,
            @RequestParam String title,
            @RequestParam String cont) {
        WriteBoardRequestDTO dto = new WriteBoardRequestDTO(writer_nm, password, title, cont, ct_cd, no);
        BoardResponseDTO board = boardService.updateBoard(dto);
        return ResponseEntity.ok().body(board);
    }

    /**
     * @param no - 게시글 번호
     * @return boolean - 삭제된 행이 1개일 때 true 반환
     * @throws BoardException (상태코드 : 503 Service Unavailable)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteBoard(@RequestParam int no) {
        Boolean result = boardService.deleteBoard(no);
        return ResponseEntity.ok().body(result);
    }

    /**
     *
     * @param no - 게시글 번호
     * @param password - 비밀번호
     * @return boolean - db에 저장되어있는 비밀번호와 일치할 때 true 반환
     * @throws BoardException -
     */
    @PostMapping("/auth")
    public ResponseEntity<Object> auth(
            @RequestParam int no,
            @RequestParam String password) {
        boolean result = boardService.authBoard(no, password);
        return ResponseEntity.ok().body(result);
    }
}
