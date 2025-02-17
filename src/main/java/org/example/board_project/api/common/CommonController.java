package org.example.board_project.api.common;

import lombok.RequiredArgsConstructor;
import org.example.board_project.model.entity.CommonCode;
import org.example.board_project.service.CommonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comm")
@RequiredArgsConstructor
public class CommonController {
    private final CommonService commonService;

    @GetMapping("/category")
    public ResponseEntity<Object> category() {
        String category = "CTG";
        List<CommonCode> list = commonService.getCategory(category);
        return ResponseEntity.ok(list);
    }
}
