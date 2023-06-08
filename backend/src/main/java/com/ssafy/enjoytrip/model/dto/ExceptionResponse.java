package com.ssafy.enjoytrip.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private String message;
    private int status;
}
