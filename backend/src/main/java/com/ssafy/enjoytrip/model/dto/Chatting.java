package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Chatting {
    private int id;
    private String nickName;
    private String message;
    private LocalDateTime time;
}
