package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.dto.Chatting;
import com.ssafy.enjoytrip.model.dto.Notify;
import com.ssafy.enjoytrip.model.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class StompController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    @MessageMapping("/plan")
    public void sendMsg(@Payload Notify notify) {
        notificationService.addNotify(notify);
        notify.setPlanLink("/plan/detail/" + notify.getPlanId());
        System.out.println(notify);
        simpMessagingTemplate.convertAndSend("/sub/", notify);
    }

    @MessageMapping("/chat")
    public void chatting(@Payload Chatting chatting) {
        notificationService.addChat(chatting);
        simpMessagingTemplate.convertAndSend("/sub/chat/", chatting);
    }
}