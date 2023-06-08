package com.ssafy.enjoytrip.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/api")
public class ApiController {

    @Value("${naver.newsClientId}")
    private String naverNewsClientId;

    @Value("${naver.newsSecret}")
    private String naverNewsSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/news/{query}")
    public ResponseEntity<?> getNews(@PathVariable String query) throws JsonProcessingException {
        String requestUrl = "https://openapi.naver.com/v1/search/news.json?query=" + query;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverNewsClientId);
        headers.set("X-Naver-Client-Secret", naverNewsSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> profile = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, Map.class);
        List<Map<Object, String>> info = (List) profile.getBody().get("items");

        return new ResponseEntity<>(info, HttpStatus.ACCEPTED);
    }
}
