package org.example.board_project.model.dto.requestDTO.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class ImageRequestDTO {
    private int image_no;
    private String origin_nm;
    private String save_nm;
    private int ref_pk;
    private String url;

    public static ImageRequestDTO create(UUID uuid, String originalFilename, int boardNo, String savePath) {
        return ImageRequestDTO.builder()
                .origin_nm(originalFilename)
                .save_nm(uuid.toString())
                .ref_pk(boardNo)
                .url(savePath)
                .build();
    }
}
