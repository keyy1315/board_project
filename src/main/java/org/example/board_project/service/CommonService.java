package org.example.board_project.service;

import lombok.RequiredArgsConstructor;
import org.example.board_project.exception.Common.CommonException;
import org.example.board_project.exception.ErrorCode;
import org.example.board_project.mapper.CommonMapper;
import org.example.board_project.model.entity.CommonCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final CommonMapper commonMapper;

    public List<CommonCode> getCategory(String groupCode) {
        List<CommonCode> list = commonMapper.getCategory(groupCode);
        if(list.isEmpty()) {
            throw new CommonException(
                    ErrorCode.NONEXISTENT_CATEGORY.getHttpStatus(),
                    ErrorCode.NONEXISTENT_CATEGORY.getMessage()
            );
        }
        return list;
    }
}
