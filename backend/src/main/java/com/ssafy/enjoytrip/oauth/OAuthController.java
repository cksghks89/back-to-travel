package com.ssafy.enjoytrip.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.model.dto.LoginLog;
import com.ssafy.enjoytrip.model.dto.User;
import com.ssafy.enjoytrip.model.service.MemberService;
import com.ssafy.enjoytrip.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    @Value("${naver.clientId}")
    private String clientId;

    @Value("${naver.clientSecret}")
    private String clientSecret;

    private final String callbackUrl = "http://localhost:8080/user/navercallback";
    private final String tokenUrl = "https://nid.naver.com/oauth2.0/token";

    @GetMapping("/info/naver")
    public Map<String, String> naverInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("clientId", clientId);
        result.put("callbackURL", callbackUrl);
        return result;
    }

    @PostMapping("/code/naver")
    public ResponseEntity<?> getNaverCode(@RequestParam String code) throws Exception {
        String requestUrl = tokenUrl + "?grant_type=authorization_code" + "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code +
                "&state=requestState";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(requestUrl, String.class);

        Map map = objectMapper.readValue(response, Map.class);

        restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + map.get("access_token"));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> profile = restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, entity, Map.class);
        Map<Object, String> info = (Map) profile.getBody().get("response");

        User user = new User();
        user.setEmailAuth(true);
        user.setEmail(info.get("email"));
        user.setNickName(info.get("nickname"));
        user.setUserId(info.get("id"));
        user.setProfileImage((String) info.get("profile_image"));
        user.setPassword("OAuth");

        User findUser = memberService.userInfo(user.getUserId());
        if (findUser == null) {
            memberService.oAuthSignUp(user);
        }

        User result = memberService.userInfo(user.getUserId());

        Map<String, Object> resultMap = new HashMap<>();
        String accessToken = jwtUtils.generateAccessToken(result.getUserId());
        String refreshToken = jwtUtils.generateRefreshToken(result.getUserId());
        resultMap.put("access-token", accessToken);
        resultMap.put("refresh-token", refreshToken);
        resultMap.put("message", "success");
        resultMap.put("userProfile", result.getProfileImage());

        LoginLog loginLog = new LoginLog();
        loginLog.setResult(true);
        loginLog.setUserId("[OAuth]" + result.getEmail());
        memberService.userLog(loginLog);

        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }
}
