package org.example.board_project.boardTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.board_project.mapper.BoardMapper;
import org.example.board_project.model.dto.responseDTO.board.BoardResponseDTO;
import org.example.board_project.model.Board;
import org.example.board_project.service.board.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BoardMapper boardMapper;


    @Test
    @DisplayName("조건에 따른 게시글 목록 조회 테스트 1 : 검색어")
    void getBoardList_search() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("category_cd", Collections.singletonList(null));
        map.put("src_cd", Collections.singletonList("전체"));
        map.put("search", Collections.singletonList("게시글"));
        map.put("sort_cd", Collections.singletonList("view_cnt"));
        map.put("page_no", Collections.singletonList("1"));
        map.put("page_size", Collections.singletonList("10"));

        String response = mockMvc.perform(get("/api/board/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Map responseMap = objectMapper.readValue(response, Map.class);
//        카테고리 - 전체, 검색조건 - 전체, 검색어 - 게시글, 정렬조건 - 조회수 내림차순, 페이지 번호 - 1page, 페이지 당 목록 수 - 10개
        assertThat(responseMap).isNotNull();
//        조건에 맞는 게시글은 총 2개
        assertThat(responseMap.get("total")).isEqualTo(2);
//        정렬조건에 따르는 첫 번째 게시글 기본 키는 2번
        List<Map<String, Object>> b = (List<Map<String, Object>>) responseMap.get("boards");
        assertThat(b.get(0).get("board_no")).isEqualTo(2);
    }

    @Test
    @DisplayName("조건에 따른 게시글 목록 조회 테스트 2 : 카테고리")
    void getBoardList_category() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("category_cd", Collections.singletonList("공지"));
        map.put("src_cd", Collections.singletonList(null));
        map.put("search", Collections.singletonList(null));
        map.put("sort_cd", Collections.singletonList("reg_dt"));
        map.put("page_no", Collections.singletonList("1"));
        map.put("page_size", Collections.singletonList("10"));

        String response = mockMvc.perform(get("/api/board/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Map responseMap = objectMapper.readValue(response, Map.class);
//        카테고리 - 공지, 검색조건, 검색어 - 없음, 정렬조건 - 날짜 내림차순, 페이지 번호 - 1, 페이지 당 목록 수 - 10개
        assertThat(responseMap).isNotNull();
//        조건에 맞는 게시글은 총 5개
        assertThat(responseMap.get("total")).isEqualTo(5);
//        정렬조건에 따르는 첫 번째 게시글 기본 키는 21번
        List<Map<String, Object>> b = (List<Map<String, Object>>) responseMap.get("boards");
        assertThat(b.get(0).get("board_no")).isEqualTo(21);
    }

    @Test
    @DisplayName("상세 게시글 조회 테스트")
    void getBoard() throws Exception {
        String response = mockMvc.perform(get("/api/board/no")
                .contentType(MediaType.APPLICATION_JSON)
                .param("no","2"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Map responseMap = objectMapper.readValue(response, Map.class);
        assertThat(responseMap).isNotNull();
//        2번 게시글 조회 테스트
        assertThat(responseMap.get("board_no")).isEqualTo(2);
        assertThat(responseMap.get("title")).isEqualTo("두 번째 게시글");
//        게시글 조회 시 조회수 업데이트 여부 확인
        assertThat(responseMap.get("view_cnt")).isEqualTo(26);
    }

    @Test
    @DisplayName("게시글 저장 테스트")
    void saveBoardTest() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("writer_nm", Collections.singletonList("tester"));
        map.put("password", Collections.singletonList("1234"));
        map.put("title", Collections.singletonList("test Title"));
        map.put("cont", Collections.singletonList("test Content"));
        map.put("category_cd", Collections.singletonList("공지"));

//      컨트롤러 status test - 200
        String response = mockMvc.perform(post("/api/board/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Map responseMap = objectMapper.readValue(response, Map.class);
//        반환된 데이터 확인
        assertThat(responseMap.get("title")).isEqualTo("test Title");
//        저장된 게시글 확인
        BoardResponseDTO respDTO = boardService.findBoard((Integer) responseMap.get("board_no"));
        assertThat(respDTO).isNotNull();
        assertThat(respDTO.getTitle()).isEqualTo("test Title");

    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updateBoardTest() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("no", Collections.singletonList("4"));
        map.put("writer_nm", Collections.singletonList("tester"));
        map.put("password", Collections.singletonList("1234"));
        map.put("category_cd", Collections.singletonList("일반"));
        map.put("title", Collections.singletonList("test Title"));
        map.put("cont", Collections.singletonList("test Content"));

        String response = mockMvc.perform(put("/api/board/update")
                .contentType(MediaType.APPLICATION_JSON)
                .params(map))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Map responseMap = objectMapper.readValue(response, Map.class);

//        반환된 데이터 확인
        assertThat(responseMap).isNotNull();
        assertThat(responseMap.get("board_no")).isEqualTo(4);
        assertThat(responseMap.get("title")).isEqualTo("test Title");
//        저장된 데이터 확인
        Board board = boardMapper.findBoard((Integer) responseMap.get("board_no"));
        assertThat(board.getTitle()).isEqualTo("test Title");
    }
    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteBoardTest() throws Exception {
        String response = mockMvc.perform(delete("/api/board/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .param("no","4"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

//      게시글 삭제시 return 값 확인
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("true");
//      실제 지워졌는지 mapper 통해 확인
        assertThat(boardMapper.findBoard(4)).isNull();
    }

    @Test
    @DisplayName("게시글 비밀번호 확인 테스트 ")
    void authBoardTest() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("no",Collections.singletonList("4"));
        map.put("password", Collections.singletonList("news123"));

        String response = mockMvc.perform(post("/api/board/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .params(map))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("true");
    }

}
