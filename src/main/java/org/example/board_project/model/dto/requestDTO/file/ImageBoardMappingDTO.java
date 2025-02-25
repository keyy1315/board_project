package org.example.board_project.model.dto.requestDTO.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ImageBoardMappingDTO {
    private String url;
    private int boardNo;

    public static ImageBoardMappingDTO create(String url, int boardNo) {
        return ImageBoardMappingDTO.builder()
                .url(url)
                .boardNo(boardNo)
                .build();
    }
}
