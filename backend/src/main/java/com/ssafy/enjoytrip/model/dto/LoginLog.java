package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

@Data
public class LoginLog {
    private int id;
    private String userId;
    private String loginTime;
    private boolean result;
}
