package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

@Data
public class Board {
    private int boardId;
    private String userId;
    private String title;
    private int hit;
    private String createTime;
    private String updateTime;
    private String content;
}
