package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.Chatting;
import com.ssafy.enjoytrip.model.dto.Notify;
import com.ssafy.enjoytrip.model.dto.User;
import com.ssafy.enjoytrip.model.mapper.NotifyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotifyMapper notifyMapper;

    @Override
    public List<Notify> getNotifyList(User user) {
        return notifyMapper.selectNotifyList(user);
    }

    @Override
    public void addNotify(Notify notify) {
        notifyMapper.insertNotify(notify);
    }

    @Override
    public void readNotification(User user) {
        notifyMapper.updateNotify(user);
    }

    @Override
    public void addChat(Chatting chatting) {
        notifyMapper.insertChatting(chatting);
    }

    @Override
    public List<Chatting> getChattingList() {
        return notifyMapper.selectChattingList();
    }
}
