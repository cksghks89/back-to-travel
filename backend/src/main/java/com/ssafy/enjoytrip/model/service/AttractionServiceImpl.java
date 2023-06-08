package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.*;
import com.ssafy.enjoytrip.model.mapper.AttractionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;

    @Override
    public List<Attraction> getPopularItem(int typeId) {
        return attractionMapper.selectItem(typeId);
    }

    @Override
    public Map<String, Object> getAttractionList(Page page) {
        Map<String, Object> result = new HashMap<>();

        List<Attraction> list = attractionMapper.selectAttractionList(page);
        result.put("attractionList", list);
        PageResult pgResult = new PageResult(page.getPageNo(), attractionMapper.selectAttractionCount(page), page.getListSize(), 10, page.getQuery());
        result.put("pageResult", pgResult);
        return result;
    }

    @Override
    public AttractionLike isLike(AttractionLike attractionLike) {
        return attractionMapper.selectAttractionLike(attractionLike);
    }

    @Override
    @Transactional
    public void doLike(AttractionLike attractionLike) {
        AttractionLike result = attractionMapper.selectAttractionLike(attractionLike);
        if (result == null) {
            attractionMapper.insertLike(attractionLike);
        } else {
            attractionMapper.deleteLike(attractionLike);
        }
    }

    @Override
    public Attraction detail(String contentId) {
        return attractionMapper.selectAttraction(contentId);
    }

    @Override
    public List<Sido> getSido() {
        return attractionMapper.selectSido();
    }

    @Override
    public List<Gugun> getGugun(int sidoCode) {
        return attractionMapper.selectGugun(sidoCode);
    }

    @Override
    public List<Attraction> searchAttractionByQuerySearch(SearchDto searchDto) {
        return attractionMapper.selectAttractionByQuerySearch(searchDto);
    }

    @Override
    public List<Attraction> searchAttractionByLocationSearch(SearchDto searchDto) {
        return attractionMapper.selectAttractionByLocationSearch(searchDto);
    }

    @Override
    public void savePlan(Plan plan, String userId) {
        plan.setUserId(userId);
        attractionMapper.insertPlan(plan);
        int planId = attractionMapper.selectPlanIdByUserId(plan);

        List<Attraction> list = plan.getAttractionSequence();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> planDetail = new HashMap<>();
            planDetail.put("planId", planId);
            planDetail.put("contentId", list.get(i).getContentId());
            planDetail.put("sequence", i + 1);
            attractionMapper.insertPlanSequence(planDetail);
        }
    }

    @Override
    public List<Plan> getPlanList(Page page) {
        List<Plan> plans = attractionMapper.selectPlanList(page);
        for (int i = 0; i < plans.size(); i++) {
            Plan plan = plans.get(i);
            plan.setAttractionSequence(new ArrayList<>());
            for (int j = 0; j < plan.getPlanSequence().size(); j++) {
                Attraction attraction = attractionMapper.selectAttraction(String.valueOf(plan.getPlanSequence().get(j).getContentId()));
                plan.getAttractionSequence().add(attraction);
            }
        }
        return plans;
    }

    @Override
    public Plan getPlanDetail(int planId) throws NotFoundException {
        Plan plan = attractionMapper.selectPlanDetail(planId);
        if (plan == null) {
            throw new NotFoundException("여행정보를 찾을 수 없습니다.");
        }
        plan.setAttractionSequence(new ArrayList<>());
        for (int j = 0; j < plan.getPlanSequence().size(); j++) {
            Attraction attraction = attractionMapper.selectAttraction(String.valueOf(plan.getPlanSequence().get(j).getContentId()));
            plan.getAttractionSequence().add(attraction);
        }
        return plan;
    }

    @Override
    @Transactional
    public void updatePlan(int planId, List<Attraction> list, User user) {
        attractionMapper.deletePlan(planId);
    }

    @Override
    public void deletePlan(int planId) {
        attractionMapper.deletePlan(planId);
    }

    public List<Attraction> getAttractionListByUserId(String userId) {
        return attractionMapper.selectAttractionListByUserId(userId);
    }

    @Override
    public List<Plan> getPlanListByUserId(String userId) {
        List<Plan> plans = attractionMapper.selectPlanListByUserId(userId);
        for (int i = 0; i < plans.size(); i++) {
            Plan plan = plans.get(i);
            plan.setAttractionSequence(new ArrayList<>());
            for (int j = 0; j < plan.getPlanSequence().size(); j++) {
                Attraction attraction = attractionMapper.selectAttraction(String.valueOf(plan.getPlanSequence().get(j).getContentId()));
                plan.getAttractionSequence().add(attraction);
            }
        }
        return plans;
    }

    @Override
    public int getHeart(AttractionLike attractionLike) {
        return attractionMapper.selectHeart(attractionLike);
    }

    @Transactional
    @Override
    public void copyPlan(Plan plan) throws NotFoundException {
        List<PlanSequence> planSequence = getPlanDetail(plan.getPlanId()).getPlanSequence();
        plan.setPlanSequence(planSequence);

        attractionMapper.insertPlanCopy(plan);
        int parmaryKey = plan.getPlanId();

        List<Attraction> list = plan.getAttractionSequence();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> planDetail = new HashMap<>();
            planDetail.put("planId", parmaryKey);
            planDetail.put("contentId", list.get(i).getContentId());
            planDetail.put("sequence", i + 1);
            attractionMapper.insertPlanSequence(planDetail);
        }
    }

    @Override
    public int getWishCount(int planId) {
        return attractionMapper.selectWishCount(planId);
    }
}
