package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.*;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;
import java.util.Map;

public interface AttractionService {
    List<Attraction> getPopularItem(int typeId);

    Map<String, Object> getAttractionList(Page page);

    AttractionLike isLike(AttractionLike attractionLike);

    void doLike(AttractionLike attractionLike);

    Attraction detail(String contentId);

    List<Sido> getSido();

    List<Gugun> getGugun(int sidoCode);

    List<Attraction> searchAttractionByQuerySearch(SearchDto searchDto);

    List<Attraction> searchAttractionByLocationSearch(SearchDto searchDto);

    void savePlan(Plan plan, String userId);

    List<Plan> getPlanList(Page page);

    Plan getPlanDetail(int planId) throws NotFoundException;

    void updatePlan(int planId, List<Attraction> list, User user);

    void deletePlan(int planId);

    List<Attraction> getAttractionListByUserId(String userId);

    List<Plan> getPlanListByUserId(String userId);

    int getHeart(AttractionLike attractionLike);

    void copyPlan(Plan plan) throws NotFoundException;

    int getWishCount(int planId);
}
