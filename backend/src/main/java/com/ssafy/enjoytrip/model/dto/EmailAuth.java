package com.ssafy.enjoytrip.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailAuth {
    private String userId;
    private String email;
    private String uuid;
    private String expired;
}
