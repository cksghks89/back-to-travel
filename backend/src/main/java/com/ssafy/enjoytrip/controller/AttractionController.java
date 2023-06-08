package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.dto.*;
import com.ssafy.enjoytrip.model.service.AttractionService;
import com.ssafy.enjoytrip.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attraction")
@CrossOrigin("*")
@Slf4j
public class AttractionController {

    private final AttractionService attractionService;
    private final JwtUtils jwtUtils;

    @GetMapping("/popular/{typeId}")
    public ResponseEntity<?> getPopularItem(@PathVariable int typeId) {
        List<Attraction> list = attractionService.getPopularItem(typeId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAttractionList(String pageNo, String type, String listSize, String query) {
        Page page = new Page();
        if (pageNo == null || "".equals(pageNo)) page.setPageNo(1);
        else page.setPageNo(Integer.parseInt(pageNo));

        if (listSize == null || "".equals(listSize)) page.setListSize(12);
        else page.setListSize(Integer.parseInt(listSize));

        page.setQuery(query);
        if (type == null || "".equals(type)) page.setType(0);
        else page.setType(Integer.parseInt(type));

        Map<String, Object> result = attractionService.getAttractionList(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/wish")
    public ResponseEntity<?> getAttractionListByUserId(HttpServletRequest request) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        List<Attraction> list = attractionService.getAttractionListByUserId(user.getUserId());
        log.info("list: {}", list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/like")
    public ResponseEntity<?> isLike(HttpServletRequest request, String contentId) throws SQLException {
        User userInfo = jwtUtils.getUserInfo(request);

        AttractionLike attractionLike = new AttractionLike();
        attractionLike.setUserId(userInfo.getUserId());
        attractionLike.setContentId(Integer.parseInt(contentId));

        AttractionLike result = attractionService.isLike(attractionLike);
        if (result == null) {
            return new ResponseEntity<>(new CommonResponse("no", 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CommonResponse("yes", 200), HttpStatus.OK);
        }
    }

    @PostMapping("/like")
    public ResponseEntity<?> doLike(HttpServletRequest request, String contentId) throws SQLException {
        User userInfo = jwtUtils.getUserInfo(request);

        AttractionLike attractionLike = new AttractionLike();
        attractionLike.setUserId(userInfo.getUserId());
        attractionLike.setContentId(Integer.parseInt(contentId));

        attractionService.doLike(attractionLike);
        log.info("attractionLike: {}", attractionLike);
        return new ResponseEntity<>(new CommonResponse("좋아요 성공", 200), HttpStatus.OK);
    }

    @GetMapping("/heart/{contentId}")
    public ResponseEntity<?> getHeart(@PathVariable String contentId) throws SQLException {
        AttractionLike attractionLike = new AttractionLike();
        attractionLike.setContentId(Integer.parseInt(contentId));

        int heartCnt = attractionService.getHeart(attractionLike);
        Map<String, Integer> map = new HashMap<>();
        map.put("heartCnt", heartCnt);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/detail/{contentId}")
    public ResponseEntity<?> detail(@PathVariable String contentId) {
        Attraction attraction = attractionService.detail(contentId);
        if (attraction == null) {
            return new ResponseEntity<>(new CommonResponse("관광지 정보를 찾을 수 없습니다.", 404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attraction, HttpStatus.OK);
    }

    @GetMapping("/sido")
    public ResponseEntity<?> getSido() {
        return new ResponseEntity<>(attractionService.getSido(), HttpStatus.OK);
    }

    @GetMapping("/gugun/{sidoCode}")
    public ResponseEntity<?> getGugun(@PathVariable int sidoCode) {
        return new ResponseEntity<>(attractionService.getGugun(sidoCode), HttpStatus.OK);
    }

    @GetMapping("/query-search")
    public ResponseEntity<?> querySearchAttraction(String sidoCode, String gugunCode, String contentTypeId, String query) throws NotFoundException {
        SearchDto searchDto = new SearchDto();
        if (sidoCode != null && !"".equals(sidoCode)) searchDto.setSidoCode(sidoCode);
        if (gugunCode != null && !"".equals(gugunCode)) searchDto.setGugunCode(gugunCode);
        if (contentTypeId != null && !"".equals(contentTypeId)) searchDto.setContentTypeId(contentTypeId);
        searchDto.setQuery(query);

        List<Attraction> attractions = attractionService.searchAttractionByQuerySearch(searchDto);
        if (attractions == null) {
            throw new NotFoundException("리스트를 조회하지 못했습니다.");
        }
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("/location-search")
    public ResponseEntity<?> locationSearchAttraction(String lat, String lng) throws NotFoundException {
        SearchDto searchDto = new SearchDto();
        searchDto.setLat(Double.parseDouble(lat));
        searchDto.setLng(Double.parseDouble(lng));

        List<Attraction> attractions = attractionService.searchAttractionByLocationSearch(searchDto);
        if (attractions == null) {
            throw new NotFoundException("리스트를 조회하지 못했습니다.");
        }
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @PostMapping("/makePlan")
    public ResponseEntity<?> makePlan(HttpServletRequest request, @RequestBody Plan plan) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        attractionService.savePlan(plan, user.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/copyPlan")
    public ResponseEntity<?> copyPlan(HttpServletRequest request, @RequestBody Plan plan) throws SQLException, NotFoundException {
        log.info("plan: {}", plan);
        User user = jwtUtils.getUserInfo(request);
        plan.setUserId(user.getUserId());
        plan.setOpen(false);
        attractionService.copyPlan(plan);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/plan/list")
    public ResponseEntity<?> planList(String pageNo, String listSize, String query) {
        Page page = new Page();
        if (pageNo == null || "".equals(pageNo)) page.setPageNo(1);
        else page.setPageNo(Integer.parseInt(pageNo));

        if (listSize == null || "".equals(listSize)) page.setListSize(10);
        else page.setListSize(Integer.parseInt(listSize));

        page.setQuery(query);

        List<Plan> planList = attractionService.getPlanList(page);
        List<Plan> resultPlanList = new ArrayList<>();
        for (Plan plan : planList) {
            if (plan.isOpen()) {
                resultPlanList.add(plan);
                plan.setWishCnt(attractionService.getWishCount(plan.getPlanId()));
            }
        }
        resultPlanList.sort((o1, o2) -> o2.getPlanId() - o1.getPlanId());

        return new ResponseEntity<>(resultPlanList, HttpStatus.OK);
    }

    @GetMapping("/plan/wish/{planId}")
    public ResponseEntity<?> getWishCount(@PathVariable int planId) {
        int count = attractionService.getWishCount(planId);
        return new ResponseEntity<>(count - 1, HttpStatus.OK);
    }

    @GetMapping("/plan/mylist")
    public ResponseEntity<?> planListByUserId(String query, HttpServletRequest request) throws SQLException {
        User user = jwtUtils.getUserInfo(request);

        List<Plan> planList = attractionService.getPlanListByUserId(user.getUserId());
        return new ResponseEntity<>(planList, HttpStatus.OK);
    }

    @GetMapping("/plan/detail/{planId}")
    public ResponseEntity<?> planDetail(@PathVariable int planId) throws NotFoundException {
        Plan plan = attractionService.getPlanDetail(planId);
        if (plan == null)
            return new ResponseEntity<>(new CommonResponse("plan 조회에 실패하였습니다.", 404), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @PutMapping("/plan/{planId}")
    public ResponseEntity<?> updatePlan(@PathVariable int planId, @RequestBody List<Attraction> list, HttpServletRequest request) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        attractionService.updatePlan(planId, list, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable int planId) {
        attractionService.deletePlan(planId);
        return new ResponseEntity<>(new CommonResponse(planId + " 삭제 성공", 200), HttpStatus.OK);
    }
}
