package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Plan {
    private int planId;
    private String userId;
    private String title;
    private boolean open;
    private int wishCnt;
    List<PlanSequence> planSequence;
    List<Attraction> attractionSequence;
}
