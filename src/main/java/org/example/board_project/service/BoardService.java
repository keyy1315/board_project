package org.example.board_project.service;

import lombok.RequiredArgsConstructor;
import org.example.board_project.exception.Board.BoardException;
import org.example.board_project.exception.ErrorCode;
import org.example.board_project.mapper.BoardMapper;
import org.example.board_project.model.dto.requestDTO.BoardListRequestDTO;
import org.example.board_project.model.dto.requestDTO.WriteBoardRequestDTO;
import org.example.board_project.model.dto.responseDTO.BoardListResponseDTO;
import org.example.board_project.model.dto.responseDTO.BoardResponseDTO;
import org.example.board_project.model.entity.Board;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    /**
     * @param categoryCode - 카테고리 코드
     * @param searchCode   - 검색 조건
     * @param search       - 검색어
     * @param sortCode     - 정렬조건
     * @param page_no      - 페이지 번호
     * @param page_size    - 게시글 출력 개수
     * @return BoardListResponseDTO - [BoardResponseDTO 의 List, 게시글 총 개수]
     * BoardResponseDTO (비밀번호 컬럼이 없는 DTO) 에 게시글의 정보를 담아
     * BoardListResponseDTO 에 찾은 게시글의 총 개수와 함께 List type 으로 담아 반환함
     */
    public BoardListResponseDTO findBoardList(
            String categoryCode,
            String searchCode,
            String search,
            String sortCode,
            int page_no,
            int page_size) {

        int offset = (page_no - 1) * page_size;

        BoardListRequestDTO sqlDTO = new BoardListRequestDTO(categoryCode, searchCode, search, sortCode, offset, page_size);
        List<Board> boards = boardMapper.findWithFilter(sqlDTO);

        if (boards.isEmpty()) {
            return BoardListResponseDTO.builder()
                    .boards(null)
                    .total(0)
                    .build();
        }

        List<BoardResponseDTO> newBoards = new ArrayList<>();
        for (Board board : boards) {
            BoardResponseDTO boardResponseDTO = conversionBoardToDTO(board);
            newBoards.add(boardResponseDTO);
        }
        return BoardListResponseDTO.builder()
                .boards(newBoards)
                .total(boardMapper.countBoard(sqlDTO))
                .build();
    }

    /**
     * @param dto - [작성자명, 비밀번호, 제목, 내용, 카테고리 코드, 게시글 번호]
     * @return BoardResponseDTO - [게시글 번호, 카데고리 코드, 제목, 내용, 작성자명, 조회수, 작성 날짜, 수정 날짜]
     */
    public BoardResponseDTO saveBoard(WriteBoardRequestDTO dto) {
        DTONullChecker(dto);
        if (boardMapper.saveBoard(dto) == 1) {
            Board board = boardMapper.findBoard(dto.getBoardNo());
            return conversionBoardToDTO(Objects.requireNonNull(board));
        } else {
            throw new BoardException(
                    ErrorCode.FAIL_TO_UPLOAD_BOARD.getHttpStatus(),
                    ErrorCode.FAIL_TO_UPLOAD_BOARD.getMessage()
            );
        }
    }

    /**
     * @param no - 게시글 번호
     * @return BoardResponseDTO - [게시글 번호, 카데고리 코드, 제목, 내용, 작성자명, 조회수, 작성 날짜, 수정 날짜]
     */
    public BoardResponseDTO findBoard(int no) {
        if (boardMapper.IncrementViewCnt(no) == 1) {
            Board board = boardMapper.findBoard(no);
            return conversionBoardToDTO(Objects.requireNonNull(board));
        } else {
            throw new BoardException(
                    ErrorCode.NONEXISTENT_BOARD.getHttpStatus(),
                    ErrorCode.NONEXISTENT_BOARD.getMessage()
            );
        }
    }

    /**
     * @param dto - [작성자명, 비밀번호, 제목, 내용, 카테고리 코드, 게시글 번호]
     * @return BoardResponseDTO - [게시글 번호, 카데고리 코드, 제목, 내용, 작성자명, 조회수, 작성 날짜, 수정 날짜]
     */
    public BoardResponseDTO updateBoard(WriteBoardRequestDTO dto) {
        if (boardMapper.findBoard(dto.getBoardNo()) == null) {
            throw new BoardException(
                    ErrorCode.NONEXISTENT_BOARD.getHttpStatus(),
                    ErrorCode.NONEXISTENT_BOARD.getMessage()
            );
        }
        DTONullChecker(dto);
        if (boardMapper.updateBoard(dto) == 1) {
            Board board = boardMapper.findBoard(dto.getBoardNo());
            return conversionBoardToDTO(Objects.requireNonNull(board));
        } else {
            throw new BoardException(
                    ErrorCode.FAIL_TO_UPDATE_BOARD.getHttpStatus(),
                    ErrorCode.FAIL_TO_UPDATE_BOARD.getMessage()
            );
        }
    }

    /**
     * @param no - 게시글 번호
     * @return boolean
     * 삭제된 행의 개수가 1개면 true 반환
     */
    public Boolean deleteBoard(int no) {
        if (boardMapper.deleteBoard(no) == 1) {
            return true;
        } else {
            throw new BoardException(
                    ErrorCode.FAIL_TO_DELETE_BOARD.getHttpStatus(),
                    ErrorCode.FAIL_TO_DELETE_BOARD.getMessage()
            );
        }
    }

    /**
     * @param board - Entity
     * @return BoardResponseDTO - [게시글 번호, 카데고리 코드, 제목, 내용, 작성자명, 조회수, 작성 날짜, 수정 날짜]
     * DB에 담긴 비밀번호를 프론트에 넘기지 않기 위해 Board Entity 를 DTO 로 변환하는 메소드
     */
    private BoardResponseDTO conversionBoardToDTO(Board board) {
        return BoardResponseDTO.builder()
                .board_no(board.getBoard_no())
                .category_cd(board.getCategory_cd())
                .title(board.getTitle())
                .cont(board.getCont())
                .writer_nm(board.getWriter_nm())
                .view_cnt(board.getView_cnt())
                .reg_dt(board.getReg_dt())
                .mod_dt(board.getMod_dt())
                .build();
    }

    private void DTONullChecker(WriteBoardRequestDTO dto) {
        if (!StringUtils.hasText(dto.getWriter_nm())) throw new IllegalArgumentException("writer name is empty");
        if (!StringUtils.hasText(dto.getPassword())) throw new IllegalArgumentException("password is empty");
        if (!StringUtils.hasText(dto.getCt_cd())) throw new IllegalArgumentException("category code is empty");
        if (!StringUtils.hasText(dto.getCont())) throw new IllegalArgumentException("content is empty");
        if (!StringUtils.hasText(dto.getTitle())) throw new IllegalArgumentException("title is empty");
    }


    public boolean authBoard(int no, String password) {
        if (!StringUtils.hasText(password)) throw new IllegalArgumentException("password is empty");
        String db_pwd = boardMapper.getBoardPassword(no);

        if (db_pwd.equals(password)) {
            return true;
        } else {
            throw new BoardException(
                    ErrorCode.NO_AUTHORITY.getHttpStatus(),
                    ErrorCode.NO_AUTHORITY.getMessage()
            );
        }
    }
}
