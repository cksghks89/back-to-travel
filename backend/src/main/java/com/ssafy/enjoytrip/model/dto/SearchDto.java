package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

@Data
public class SearchDto {
    private String sidoCode;
    private String gugunCode;
    private String contentTypeId;
    private String query;
    private double lat;
    private double lng;
}
