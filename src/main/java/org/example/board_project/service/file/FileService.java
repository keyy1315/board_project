package org.example.board_project.service.file;

import lombok.Getter;
import lombok.Setter;
import org.example.board_project.exception.ErrorCode;
import org.example.board_project.exception.File.FileException;
import org.example.board_project.mapper.FileMapper;
import org.example.board_project.model.BoardFile;
import org.example.board_project.model.dto.requestDTO.file.ImageBoardMappingDTO;
import org.example.board_project.model.dto.requestDTO.file.ImageRequestDTO;
import org.example.board_project.model.dto.responseDTO.file.FileResponseDTO;
import org.example.board_project.model.dto.responseDTO.file.GetOriginNameFileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class FileService {
    private static final String UPLOAD_DIRECTORY = "C:\\upload";
    private final FileMapper fileMapper;
    @Getter
    @Setter
    private String imgUploadPath;

    @Autowired
    public FileService(FileMapper fileMapper, @Value("${file.upload-dir}") String imgUploadPath) {
        this.fileMapper = fileMapper;
        this.imgUploadPath = imgUploadPath;
    }

    /**
     * @param file_no - 파일 pk
     * @return - 다운로드 할 파일
     * 파일 pk로 저장된 파일 데이터 조회, 파일 객체 생성 후 반환
     */
    public GetOriginNameFileDTO downloadFile(int file_no) throws UnsupportedEncodingException {
        BoardFile uploadFile = fileMapper.getFile(file_no);

        File file = new File(UPLOAD_DIRECTORY + "\\" + uploadFile.getSave_file_nm() + "." + uploadFile.getExt());
        if (!file.exists()) {
            throw new FileException(
                    ErrorCode.FAIL_TO_DOWNLOAD_FILE.getHttpStatus(),
                    ErrorCode.FAIL_TO_DOWNLOAD_FILE.getMessage()
            );
        }
        String encodedFileName = URLEncoder.encode(uploadFile.getOrigin_file_nm(), "UTF-8").replaceAll("\\+", "%20");

        return GetOriginNameFileDTO.builder()
                .originName(encodedFileName)
                .file(file)
                .build();
    }

    /**
     * @param files       - 업로드 할 파일 리스트
     * @param board_no    - 게시글 pk
     * @param category_cd - 카테고리 코드
     * @return - 저장된 파일 리스트
     * @throws IOException - createDirectories, transferTo : file in out 을 위한 Exception 처리
     */
    @Transactional
    public List<FileResponseDTO> uploadFiles(List<MultipartFile> files, int board_no, String category_cd) throws IOException {
        List<FileResponseDTO> fileList = new ArrayList<>();
        if (files == null) {
            return fileList;
        }

        Path savePath = Paths.get(UPLOAD_DIRECTORY);
        if (!Files.exists(savePath)) {
            Files.createDirectories(savePath);
        }
        for (MultipartFile file : files) {
            BoardFile boardFile = BoardFile.create(file, savePath.toString(), board_no, category_cd);
            if (fileMapper.saveFile(boardFile) == 1) {
                FileResponseDTO fileResponseDTO = FileResponseDTO.create(boardFile);
                fileList.add(fileResponseDTO);
                Path filePath = savePath.resolve(fileResponseDTO.getSave_file_nm() + "." + fileResponseDTO.getExt());
                file.transferTo(filePath.toFile());
            } else {
                throw new FileException(
                        ErrorCode.FAIL_TO_UPLOAD_FILE.getHttpStatus(),
                        ErrorCode.FAIL_TO_UPLOAD_FILE.getMessage());
            }
        }
        return fileList;
    }

    /**
     * @param boardNo - 게시글 pk
     * @return - 게시글 당 저장된 파일들 조회
     */
    public List<FileResponseDTO> getFilesByBoardNo(int boardNo) {
        return fileMapper.getFilesByBoardNo(boardNo);
    }

    /**
     * @param deleteFiles - 삭제 할 파일 pk 리스트
     * @param uploadFiles - 업로드 할 파일 리스트
     * @param boardNo     - 게시글 pk
     * @param category_cd - 카테고리 코드
     * @return - 수정된 파일 리스트 반환
     * @throws IOException - multipart
     */
    @Transactional
    public List<FileResponseDTO> updateFiles(List<Integer> deleteFiles, List<MultipartFile> uploadFiles, int boardNo, String category_cd) throws IOException {
        List<Integer> deletedNos = deleteFiles.stream().distinct().toList();

        uploadFiles.removeIf(file -> !StringUtils.hasText(file.getOriginalFilename()));

        List<FileResponseDTO> modifiedFiles = new ArrayList<>();
        if (!uploadFiles.isEmpty()) {
            modifiedFiles = uploadFiles(uploadFiles, boardNo, category_cd);
        }
        for (Integer i : deletedNos) {
            if (fileMapper.deleteFiles(i) != 1) {
                throw new FileException(
                        ErrorCode.FAIL_TO_UPLOAD_FILE.getHttpStatus(),
                        ErrorCode.FAIL_TO_UPLOAD_FILE.getMessage()
                );
            }
        }

        return modifiedFiles;
    }

    /**
     * @param fileNo - 파일 pk
     * @return - 파일 pk로 파일 데이터 삭제
     */
    public boolean deleteFile(int fileNo) {
        return fileMapper.deleteFiles(fileNo) == 1;
    }

    public void deleteFileByBoardNo(int no) {
        fileMapper.deleteFileByBoardNo(no);
        fileMapper.deleteImagesByBoardNo(no);
    }

    public List<String> uploadImages(List<MultipartFile> file, int boardNo) throws IOException {
        List<String> urlString = new ArrayList<>();

        Path savePath = Paths.get(this.imgUploadPath);
        if (!Files.exists(savePath)) {
            Files.createDirectories(savePath);
        }

        for (MultipartFile multipartFile : file) {
            UUID uuid = UUID.randomUUID();
            String ext =
                    multipartFile.getOriginalFilename()
                            .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

            String url = "http://localhost:8080/img/" + uuid + "." + ext;
            ImageRequestDTO dto = ImageRequestDTO.create(
                    uuid, multipartFile.getOriginalFilename(), boardNo, url);
            if (fileMapper.saveImage(dto) == 1) {
                Path filePath = savePath.resolve(uuid + "." + ext);
                multipartFile.transferTo(filePath.toFile());
                urlString.add(url);
            }
        }
        return urlString;
    }

    public List<Boolean> updateBoardNo(List<String> urls, int boardNo) {
        List<Boolean> result = new ArrayList<>();
        for (String url : urls) {
            ImageBoardMappingDTO dto = ImageBoardMappingDTO.create(url, boardNo);
            if (fileMapper.updateBoardNo(dto) == 1) {
                result.add(true);
            } else {
                result.add(false);
            }
        }
        return result;
    }

    public void updateImages(int boardNo, List<String> urls) {
        List<ImageRequestDTO> images = fileMapper.findImagesWithBoardNo(boardNo);

        List<String> dbUrls = images.stream().map(ImageRequestDTO::getUrl).toList();

        List<String> deleteUrls = dbUrls.stream().filter(url -> urls.stream().noneMatch(url::equals)).toList();

        for (String deleteUrl : deleteUrls) {
            fileMapper.deleteImageByUrl(deleteUrl);
        }
    }
}
