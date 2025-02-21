package org.example.board_project.model.dto.responseDTO.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Getter
@AllArgsConstructor
@Builder
public class GetOriginNameFileDTO {
    private String originName;
    private File file;
}
