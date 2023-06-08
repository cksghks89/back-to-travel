package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

@Data
public class Notice {
    private int noticeId;
    private String userId;
    private String title;
    private int hit;
    private String createTime;
    private String updateTime;
    private String content;
}
