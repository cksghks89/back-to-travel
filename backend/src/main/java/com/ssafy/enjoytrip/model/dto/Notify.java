package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

@Data
public class Notify {
    private int id;
    private String senderNickName;
    private String receiverNickName;
    private String title;
    private String content;
    private boolean isView;
    private int planId;

    private String planLink;
}
