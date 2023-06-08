package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.model.dto.*;
import com.ssafy.enjoytrip.model.service.MemberService;
import com.ssafy.enjoytrip.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class MemberController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    @Value("${file.path}")
    private String uploadPath;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws SQLException {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        User loginUser = memberService.login(user);

        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());

        if (loginUser != null) {
            if (!loginUser.isEmailAuth()) {
                loginLog.setResult(false);
                memberService.userLog(loginLog);
                throw new UnAuthorizedException("이메일 인증이 완료되지 않은 계정입니다.");
            }
            String accessToken = jwtUtils.generateAccessToken(loginUser.getUserId());
            String refreshToken = jwtUtils.generateRefreshToken(loginUser.getUserId());
            resultMap.put("access-token", accessToken);
            resultMap.put("refresh-token", refreshToken);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;

            loginLog.setResult(true);
            memberService.userLog(loginLog);
        } else {
            loginLog.setResult(false);
            memberService.userLog(loginLog);
            throw new UnAuthorizedException("로그인 실패");
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) throws SQLException {
        return new ResponseEntity<>(jwtUtils.getUserInfo(request), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user, HttpServletRequest request) throws Exception {
        String domain = request.getRequestURL().toString().replace(request.getRequestURI().toString(), "");
        memberService.signUp(user, domain);
        return new ResponseEntity<>(new CommonResponse("회원가입 성공", 200), HttpStatus.OK);
    }

    @GetMapping("/email-auth/{userId}/{email}/{UUID}")
    public ResponseEntity<?> emailAuth(@PathVariable String userId, @PathVariable String email, @PathVariable String UUID) throws SQLException {
        EmailAuth auth = EmailAuth.builder().userId(userId).email(email).uuid(UUID).build();
        memberService.emailAuth(auth);
        return new ResponseEntity<>(new CommonResponse("이메일 인증 성공", 200), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request, @PathVariable String userId) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        if (!user.getUserId().equals(userId)) throw new UnAuthorizedException("로그인 사용자와 요청 아이디가 다릅니다.");

        return new ResponseEntity<>(new CommonResponse(userId + " 정보 반환 성공", 200, user), HttpStatus.OK);
    }

    @PostMapping("/findPassword")
    public ResponseEntity<?> findPassword(@RequestBody User user) throws Exception {
        memberService.findPassword(user);
        return new ResponseEntity<>(new CommonResponse("재발급 비밀번호가 발송되었습니다.", 200), HttpStatus.OK);
    }

    /**
     * access-token, refresh-token 재발급
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        log.info("refreshToken");
        String refreshToken = request.getHeader("refresh-token");
        log.info("refreshToken: {}", refreshToken);
        if (!jwtUtils.checkToken(refreshToken)) throw new UnAuthorizedException("refresh-token이 만료되었습니다.");

        log.info("아무문자열");

        Map<String, String> resultMap = new HashMap<>();
        String userId = jwtUtils.getUserId(refreshToken);
        String newAccessToken = jwtUtils.generateAccessToken(userId);
        String newRefreshToken = jwtUtils.generateRefreshToken(userId);
        log.info("resultMap: {}", resultMap);
        resultMap.put("access-token", newAccessToken);
        resultMap.put("refresh-token", newRefreshToken);
        resultMap.put("message", SUCCESS);

        return new ResponseEntity<>(resultMap, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        memberService.deleteUser(user.getUserId());
        return new ResponseEntity<>(new CommonResponse("회원 탈퇴 성공", 200), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody User user) throws SQLException {
        User loginUser = jwtUtils.getUserInfo(request);
        if (!loginUser.getUserId().equals(user.getUserId())) throw new UnAuthorizedException("로그인 사용자와 요청 아이디가 다릅니다.");
        memberService.updateUser(user);
        return new ResponseEntity<>(new CommonResponse("회원 정보 수정 성공", 200), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(uploadPath);
        if (!file.isEmpty()) {
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = uploadPath + File.separator + today;

            File folder = new File(saveFolder);
            if (!folder.exists())
                folder.mkdirs();

            FileInfoDto fileInfoDto = new FileInfoDto();
            String originalFileName = file.getOriginalFilename();
            if (!originalFileName.isEmpty()) {
                String saveFileName = UUID.randomUUID().toString()
                        + originalFileName.substring(originalFileName.lastIndexOf('.'));
                log.info(saveFileName);
                fileInfoDto.setSaveFolder(today);
                fileInfoDto.setOriginalFile(originalFileName);
                fileInfoDto.setSaveFile(saveFileName);
                file.transferTo(new File(folder, saveFileName));
            }
        }

        return null;
    }

    @GetMapping("/nickname/{nickName}")
    public ResponseEntity<?> checkNickName(HttpServletRequest request, @PathVariable String nickName) {
        Map<String, String> map = new HashMap<>();
        map.put("nickName", nickName);
        map.put("userId", jwtUtils.getUserId(request.getHeader("access-token")));
        int isExist = memberService.checkNickName(map);
        if (isExist >= 1) return new ResponseEntity<>(new CommonResponse("닉네임 중복", 409), HttpStatus.CONFLICT);
        else return new ResponseEntity<>(new CommonResponse("닉네임 사용 가능", 200), HttpStatus.OK);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<?> getUserNickName(@PathVariable String userId) throws SQLException {
        return new ResponseEntity<>(memberService.userInfo(userId), HttpStatus.OK);
    }

}
