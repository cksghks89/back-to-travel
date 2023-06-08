package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

@Data
public class Attraction {
    private int contentId;
    private int contentTypeId;
    private String title;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String tel;
    private String firstImage;
    private String firstImage2;
    private int readCount;
    private int sidoCode;
    private int gugunCode;
    private double latitude;
    private double longitude;
    private String mlevel;

    /* 여기부터는 join 에서 데이터를 가져와야 함 - 필요한 것이 있다면 추가 */
    private String overview;
}
