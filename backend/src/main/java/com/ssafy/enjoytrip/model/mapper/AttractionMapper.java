package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AttractionMapper {
    List<Attraction> selectItem(int typeId);

    List<Attraction> selectAttractionList(Page page);

    int selectAttractionCount(Page page);

    AttractionLike selectAttractionLike(AttractionLike attractionLike);

    void insertLike(AttractionLike attractionLike);

    void deleteLike(AttractionLike attractionLike);

    Attraction selectAttraction(String contentId);

    List<Sido> selectSido();

    List<Gugun> selectGugun(int sidoCode);

    List<Attraction> selectAttractionByQuerySearch(SearchDto searchDto);

    List<Attraction> selectAttractionByLocationSearch(SearchDto searchDto);

    void insertPlan(Plan userId);

    int selectPlanIdByUserId(Plan userId);

    void insertPlanSequence(Map<String, Object> planDetail);

    List<Plan> selectPlanList(Page page);

    Plan selectPlanDetail(int planId);

    void deletePlan(int planId);

    List<Attraction> selectAttractionListByUserId(String userId);

    List<Plan> selectPlanListByUserId(String userId);

    int selectHeart(AttractionLike attractionLike);

    void insertPlanCopy(Plan plan);

    int selectWishCount(int planId);
}
