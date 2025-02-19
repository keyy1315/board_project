package org.example.board_project.api.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.board_project.exception.Board.BoardException;
import org.example.board_project.model.dto.requestDTO.board.BoardListRequestDTO;
import org.example.board_project.model.dto.requestDTO.board.WriteBoardRequestDTO;
import org.example.board_project.model.dto.responseDTO.board.BoardListResponseDTO;
import org.example.board_project.model.dto.responseDTO.board.BoardResponseDTO;
import org.example.board_project.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    /**
     * @param category_cd - 카테고리 코드 (전체(null), 공지, 중요, 일반)
     * @param src_cd      - 검색 조건 (전체, 제목, 내용, 제목+내용, 작성자명)
     * @param search      - 검색어
     * @param sort_cd     - 정렬 조건 (최근 작성일, 조회수)
     * @param page_no     - 현재 페이지 번호 : NOTNULL!!
     * @param page_size   - 페이지 당 나타낼 게시글 목록 수 : NOTNULL!!
     * @return BoardListResponseDTO(List < Board > - 게시글 목록, int - 검색총수))
     * 조건에 따른 게시글이 하나도 없을 때는 Exception 처리 없이 List : null, int : 0 을 반환한다.
     */
    @GetMapping("/")
    public ResponseEntity<Object> getBoardList(
            @RequestParam(required = false) String category_cd,
            @RequestParam(required = false) String src_cd,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort_cd,
            @RequestParam int page_no,
            @RequestParam int page_size) {
        int offset = (page_no - 1) * page_size;
        BoardListRequestDTO DTO = BoardListRequestDTO.create(category_cd, src_cd, search, sort_cd, offset, page_size);
        BoardListResponseDTO boards = boardService.findBoardList(DTO);

        log.info("DTO : {}",DTO.toString());
        log.info("Boards : {}",boards.toString());

        return ResponseEntity.ok().body(boards);
    }

    /**
     * @param no - 게시글 번호
     * @return Board
     * @throws BoardException (상태코드 : 404 Not Found)
     *                        게시글 조회 : board_no(게시글 번호)를 통해 게시글 조회수 증가 후 게시글 객체 반환
     *                        번호에 맞는 게시글이 존재하지 않을 때 404 Error 반환
     */
    @GetMapping("/{no}")
    public ResponseEntity<Object> getBoard(@PathVariable int no) {
        BoardResponseDTO board = boardService.findBoard(no);
        return ResponseEntity.ok().body(board);
    }

    /**
     * @param writer_nm   - 작성자명
     * @param password    - 비밀번호
     * @param category_cd - 카테고리 코드
     * @param title       - 제목
     * @param cont        - 내용
     * @return Board      - 작성된 게시글 객체
     * @throws BoardException (상태코드 : 503 Service Unavailable)
     *                        게시글 작성 : 게시글 작성 후 게시글 객체 반환
     *                        작성에 실패하면 503 Error 반환
     */
    @PostMapping("/")
    public ResponseEntity<Object> writeBoard(
            @RequestParam String writer_nm,
            @RequestParam String password,
            @RequestParam String category_cd,
            @RequestParam String title,
            @RequestParam String cont) {
        WriteBoardRequestDTO dto = new WriteBoardRequestDTO(writer_nm, password, title, cont, category_cd, 0);
        BoardResponseDTO board = boardService.saveBoard(dto);
        return ResponseEntity.ok().body(board);
    }


    /**
     * @param no          - 게시글 번호
     * @param writer_nm   - 작성자명
     * @param password    - 비밀번호
     * @param category_cd - 카테고리 코드
     * @param title       - 제목
     * @param cont        - 내용
     * @return Board
     * @throws BoardException (상태코드 : 503 Service Unavailable)
     *                        게시글 수정 : 게시글 수정 후 게시글 객체 반환
     *                        수정에 실패하면 503 Error 반환
     */
    @PutMapping("/")
    public ResponseEntity<Object> updateBoard(
            @RequestParam int no,
            @RequestParam String writer_nm,
            @RequestParam String password,
            @RequestParam String category_cd,
            @RequestParam String title,
            @RequestParam String cont) {
        WriteBoardRequestDTO dto = new WriteBoardRequestDTO(writer_nm, password, title, cont, category_cd, no);
        BoardResponseDTO board = boardService.updateBoard(dto);
        return ResponseEntity.ok().body(board);
    }

    /**
     * @param no - 게시글 번호
     * @return boolean - 삭제된 행이 1개일 때 true 반환
     * @throws BoardException (상태코드 : 503 Service Unavailable)
     *                        게시글 삭제 : 게시글 삭제 후 삭제 상태 반환
     *                        삭제에 실패하면 503 Error 반환
     */
    @DeleteMapping("/")
    public ResponseEntity<Object> deleteBoard(@RequestParam int no) {
        Boolean result = boardService.deleteBoard(no);
        return ResponseEntity.ok().body(result);
    }

    /**
     * @param no       - 게시글 번호
     * @param password - 비밀번호
     * @return boolean - db에 저장되어있는 비밀번호와 일치할 때 true 반환
     * @throws BoardException (상태코드 : 401 Unauthorized)
     *                        비밀번호 확인 : 비밀번호 입력 후 일치 여부 반환
     *                        비밀번호가 맞지 않으면 401 Error 반환
     */
    @PostMapping("/auth")
    public ResponseEntity<Object> auth(
            @RequestParam int no,
            @RequestParam String password) {
        boolean result = boardService.authBoard(no, password);
        return ResponseEntity.ok().body(result);
    }
}
