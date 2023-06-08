package com.ssafy.enjoytrip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private String message;
    private int status;
    private Object data;

    public CommonResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
