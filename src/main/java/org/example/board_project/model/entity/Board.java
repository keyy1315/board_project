package org.example.board_project.model.entity;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Alias("Board")
public class Board {
    private int board_no;
    private String category_cd;
    private String title;
    private String cont;
    private String writer_nm;
    private String password;
    private int view_cnt;
    private LocalDateTime reg_dt;
    private LocalDateTime mod_dt;
}
