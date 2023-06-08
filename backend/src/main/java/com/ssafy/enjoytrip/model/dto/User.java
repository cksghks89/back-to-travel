package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String email;
    private String password;
    private String nickName;
    private String gender;
    private boolean emailAuth;
    private String profileImage;
    private String createTime;
    private String updateTime;
}
