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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    /**
     * @param dto - [category_cd, searchCode, search, sortCode, offset, size]
     *            - category_cd  : 카테고리 코드
     *            - searchCode   : 검색 조건
     *            - search       : 검색어
     *            - sortCode     : 정렬조건
     *            - offset       : 데이터 출력 시작 인덱스 (ex: offset=3, 4번부터 출력)
     *            - size         : 게시글 출력 개수
     * @return BoardListResponseDTO - [BoardResponseDTO 의 List, 게시글 총 개수]
     * BoardResponseDTO (비밀번호 컬럼이 없는 DTO) 에 게시글의 정보를 담아
     * BoardListResponseDTO 에 찾은 게시글의 총 개수와 함께 List type 으로 담아 반환함
     */
    public BoardListResponseDTO findBoardList(BoardListRequestDTO dto) {
        List<Board> boards = boardMapper.findWithFilter(dto);

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
                .total(boardMapper.countBoard(dto))
                .build();
    }

    /**
     * @param dto - [작성자명, 비밀번호, 제목, 내용, 카테고리 코드, 게시글 번호]
     * @return BoardResponseDTO - [게시글 번호, 카데고리 코드, 제목, 내용, 작성자명, 조회수, 작성 날짜, 수정 날짜]
     */
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public Boolean deleteBoard(int no) {
//      DB 에 게시글 존재하는 지 확인, 없으면 throw Board Exception (404)
        if(boardMapper.findBoard(no) == null) {
            throw new BoardException(
                    ErrorCode.NONEXISTENT_BOARD.getHttpStatus(),
                    ErrorCode.NONEXISTENT_BOARD.getMessage()
            );
        }
//      delete 된 행의 개수가 1개면 (delete = true) true 반환
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
     * @param no       - 게시글 번호
     * @param password - 게시글 비밀번호
     * @return boolean - 비밀번호 일치 여부
     * 비밀번호에 빈 값이 들어오면 BoardException (400) 반환
     * 게시글 번호에 비밀번호가 저장되지 않았거나 찾을 수 없는 게시글이라면 NonExistent Board Exception (404) 반환
     * DB 에 저장된 비밀번호와 일치하다면 true 반환
     */
    public boolean authBoard(int no, String password) {
        if (!StringUtils.hasText(password)) throw new BoardException(
                ErrorCode.DTO_OBJECT_IS_EMPTY.getHttpStatus(),
                "password is empty"
        );
        String db_pwd = boardMapper.getBoardPassword(no);
        if (db_pwd == null) {
            throw new BoardException(
                    ErrorCode.NONEXISTENT_BOARD.getHttpStatus(),
                    "Cannot find Board or Board Password"
            );
        }

        return db_pwd.equals(password);
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

    /**
     * @param dto - WriteBoardRequestDTO
     *            클라이언트에게 받은 데이터에 빈 값이 있는지 확인하는 메소드
     *            빈 값이 있다면 BoardException (Bad Request error (400))을 반환한다.
     */
    private void DTONullChecker(WriteBoardRequestDTO dto) {
        if (!StringUtils.hasText(dto.getWriter_nm())) throw new BoardException(
                ErrorCode.DTO_OBJECT_IS_EMPTY.getHttpStatus(),
                "Writer name is empty"
        );
        if (!StringUtils.hasText(dto.getPassword())) throw new BoardException(
                ErrorCode.DTO_OBJECT_IS_EMPTY.getHttpStatus(),
                "Password is empty"
        );
        if (!StringUtils.hasText(dto.getCategory_cd())) throw new BoardException(
                ErrorCode.DTO_OBJECT_IS_EMPTY.getHttpStatus(),
                "Category Code is empty"
        );
        if (!StringUtils.hasText(dto.getCont())) throw new BoardException(
                ErrorCode.DTO_OBJECT_IS_EMPTY.getHttpStatus(),
                "Content is empty"
        );
        if (!StringUtils.hasText(dto.getTitle())) throw new BoardException(
                ErrorCode.DTO_OBJECT_IS_EMPTY.getHttpStatus(),
                "Title is empty"
        );
    }

}
