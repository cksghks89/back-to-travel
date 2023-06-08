package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.Notice;
import com.ssafy.enjoytrip.model.dto.Page;

import java.util.Map;

public interface NoticeService {

    void deleteNotice(int noticeId);

    void modifyNotice(int noticeId, Notice notice);

    Notice getNoticeDetail(int noticeId);

    void writeNotice(Notice notice);

    Map<String, Object> getNoticeList(Page page);
}
