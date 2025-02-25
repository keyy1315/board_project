package org.example.board_project.api.file;

import lombok.RequiredArgsConstructor;
import org.example.board_project.model.dto.responseDTO.file.GetOriginNameFileDTO;
import org.example.board_project.service.file.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * @param file_no - 파일 pk
     * @return 파일 다운로드 Resource
     * 파일 다운로드 기능 - 파일 pk를 통한 파일 객체 생성 후 Resource 반환
     * - 다운로드 한 파일은 origin_file_nm 으로 저장됨
     */
    @GetMapping("/{file_no}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int file_no) throws UnsupportedEncodingException {
        GetOriginNameFileDTO fileDTO = fileService.downloadFile(file_no);

        Resource resource = new FileSystemResource(fileDTO.getFile());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getOriginName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    /**
     * @param file_no - 파일 pk
     * @return boolean - 파일 pk로 저장된 파일 데이터 삭제
     */
    @DeleteMapping("/{file_no}")
    public ResponseEntity<Object> deleteFile(@PathVariable int file_no) {
        boolean result = fileService.deleteFile(file_no);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestPart("image") MultipartFile file) throws IOException {
        String uploadDIR = "C:\\Users\\SAMSUNG\\IdeaProjects\\board_project\\src\\main\\resources\\static\\upload\\";
        File saveFile = new File(uploadDIR + file.getOriginalFilename());
        file.transferTo(saveFile);
        return ResponseEntity.ok().body(file.getOriginalFilename());
    }

    @PostMapping("/img")
    public ResponseEntity<Object> uploadImg(
            @RequestPart("image") List<MultipartFile> files,
            @RequestParam int boardNo
    ) throws IOException {
        List<String> urls = fileService.uploadImages(files, boardNo);
        return ResponseEntity.ok().body(urls);
    }
}
