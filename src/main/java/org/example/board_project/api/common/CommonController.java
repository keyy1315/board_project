package org.example.board_project.api.common;


import lombok.RequiredArgsConstructor;
import org.example.board_project.model.Common;
import org.example.board_project.service.common.CommonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonController {
    private final CommonService commonService;

    /**
     * @return category List - 카테고리 리스트 객체
     * 드롭다운 리스트 구현을 위한 카테고리 조회
     */
    @GetMapping("/category")
    public ResponseEntity<Object> getCategory() {
        List<Common> dto = commonService.getCategory();
        return ResponseEntity.ok(dto);
    }
}
