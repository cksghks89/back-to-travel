package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.model.dto.LoginLog;
import com.ssafy.enjoytrip.model.dto.User;
import com.ssafy.enjoytrip.model.service.MemberService;
import com.ssafy.enjoytrip.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    @GetMapping("/log")
    public ResponseEntity<?> getLogList(HttpServletRequest request) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        if (!"admin".equals(user.getUserId())) {
            throw new UnAuthorizedException("관리자 계정이 아닙니다.");
        }

        List<LoginLog> logList = memberService.getLogList();
        System.out.println(logList);
        return new ResponseEntity<>(logList, HttpStatus.OK);
    }

    @GetMapping("/userlist")
    public ResponseEntity<?> getUserList(HttpServletRequest request) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        if (!"admin".equals(user.getUserId())) {
            throw new UnAuthorizedException("관리자 계정이 아닙니다.");
        }

        List<User> userList = memberService.getUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<?> removeUser(HttpServletRequest request, @PathVariable String userId) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        if (!"admin".equals(user.getUserId())) {
            throw new UnAuthorizedException("관리자 계정이 아닙니다.");
        }
        memberService.removeUser(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}




