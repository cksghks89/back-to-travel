package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.dto.Chatting;
import com.ssafy.enjoytrip.model.dto.Notify;
import com.ssafy.enjoytrip.model.dto.User;
import com.ssafy.enjoytrip.model.service.NotificationService;
import com.ssafy.enjoytrip.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/notify")
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtUtils jwtUtils;

    @GetMapping("/list")
    public List<Notify> getNotifyList(HttpServletRequest request) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        List<Notify> notifyList = notificationService.getNotifyList(user);
        for (Notify n : notifyList) {
            n.setPlanLink("/plan/detail/" + n.getPlanId());
        }
        System.out.println(notifyList);
        return notifyList;
    }

    @PutMapping("/read")
    public void readNotification(HttpServletRequest request) throws SQLException {
        User user = jwtUtils.getUserInfo(request);
        notificationService.readNotification(user);
        log.info("read {}", user.getUserId());
    }

    @GetMapping("/chat")
    public List<Chatting> getChattingList() {
        return notificationService.getChattingList();
    }

}
