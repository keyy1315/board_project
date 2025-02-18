package org.example.board_project.service.common;

import lombok.RequiredArgsConstructor;
import org.example.board_project.mapper.CommonMapper;
import org.example.board_project.model.entity.Common;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final CommonMapper commonMapper;
    public List<Common> getCategory() {
        return commonMapper.getCategory();
    }
}
