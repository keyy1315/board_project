package org.example.board_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


/*
mybatis 저장, 반환 객체
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardFile {
    private int file_no;
    private String origin_file_nm;
    private String save_file_nm;
    private String save_path;
    private String ext;
    private int size;
    private String ref_tbl;
    private String ref_pk;
    private String ref_key;
    private int download_cnt;
    private int ord;
    private LocalDateTime reg_dt;

//    파일 데이터 저장 시 필요한 Board 객체 create 메소드
    public static BoardFile create(MultipartFile file, String save_path, int board_no, String category_cd) {
        UUID uuid = UUID.randomUUID();

        return BoardFile.builder()
                .origin_file_nm(file.getOriginalFilename())
                .save_file_nm(uuid.toString())
                .save_path(save_path)
                .ext(Objects.requireNonNull(
                        file.getOriginalFilename())
                        .substring(file.getOriginalFilename().lastIndexOf(".") + 1))
                .size((int) file.getSize())
                .ref_tbl("bt_tb_board")
                .ref_pk(String.valueOf(board_no))
                .ref_key(category_cd)
                .build();
    }
}
