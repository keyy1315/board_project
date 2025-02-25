package org.example.board_project.model.dto.responseDTO.file;

import lombok.Builder;
import lombok.Getter;
import org.example.board_project.model.BoardFile;

@Builder
@Getter
public class FileResponseDTO {
    private int file_no;
    private String origin_file_nm;
    private String save_file_nm;
    private String ext;

//      객체 생성을 위한 create 메소드
    public static FileResponseDTO create(BoardFile boardFile) {
       return FileResponseDTO.builder()
               .file_no(boardFile.getFile_no())
               .origin_file_nm(boardFile.getOrigin_file_nm())
               .save_file_nm(boardFile.getSave_file_nm())
               .ext(boardFile.getExt())
               .build();
    }
}

