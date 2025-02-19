package org.example.board_project.api.file;

import lombok.RequiredArgsConstructor;
import org.example.board_project.service.file.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/")
    public ResponseEntity<Resource> downloadFile(@PathVariable int file_no) {
        File file = fileService.downloadFile(file_no);
    }
}
