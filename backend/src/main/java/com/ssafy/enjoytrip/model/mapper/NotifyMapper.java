package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.dto.Chatting;
import com.ssafy.enjoytrip.model.dto.Notify;
import com.ssafy.enjoytrip.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotifyMapper {
    List<Notify> selectNotifyList(User user);

    void insertNotify(Notify notify);

    void updateNotify(User user);

    void insertChatting(Chatting chatting);

    List<Chatting> selectChattingList();
}
