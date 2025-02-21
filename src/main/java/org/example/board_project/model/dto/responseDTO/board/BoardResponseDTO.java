package org.example.board_project.model.dto.responseDTO.board;

import lombok.*;
import org.example.board_project.model.dto.responseDTO.file.FileResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * response 객체
 * Board Entity 자체를 넘기지 않게 하기 위해 구현
 * Board Entity 에서 password 값만 제외하고 구현
 */
@AllArgsConstructor
@Getter
@Builder
@RequiredArgsConstructor
@ToString
public class BoardResponseDTO {
    private int board_no;
    private String category_cd;
    private String title;
    private String cont;
    private String writer_nm;
    private int view_cnt;
    private LocalDateTime reg_dt;
    private LocalDateTime mod_dt;
    private List<FileResponseDTO> files;

//    파일 수정/등록 시 게시글 dto 에 files 정의를 위한 of 메소드
    public static BoardResponseDTO of(BoardResponseDTO dto, List<FileResponseDTO> files) {
        return BoardResponseDTO.builder()
                .board_no(dto.getBoard_no())
                .category_cd(dto.getCategory_cd())
                .title(dto.getTitle())
                .cont(dto.getCont())
                .writer_nm(dto.getWriter_nm())
                .view_cnt(dto.getView_cnt())
                .mod_dt(dto.getMod_dt())
                .reg_dt(dto.getReg_dt())
                .files(files)
                .build();
    }
}
