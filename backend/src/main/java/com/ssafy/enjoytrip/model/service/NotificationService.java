package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.Chatting;
import com.ssafy.enjoytrip.model.dto.Notify;
import com.ssafy.enjoytrip.model.dto.User;

import java.util.List;

public interface NotificationService {
    List<Notify> getNotifyList(User user);

    void addNotify(Notify notify);

    void readNotification(User user);

    void addChat(Chatting chatting);

    List<Chatting> getChattingList();
}
