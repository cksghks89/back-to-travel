package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.Notice;
import com.ssafy.enjoytrip.model.dto.Page;
import com.ssafy.enjoytrip.model.dto.PageResult;
import com.ssafy.enjoytrip.model.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeMapper noticeMapper;


    @Override
    public void deleteNotice(int noticeId) {
        noticeMapper.deleteNotice(noticeId);
    }

    @Override
    public void modifyNotice(int noticeId, Notice notice) {
        notice.setNoticeId(noticeId);
        noticeMapper.updateNotice(notice);
    }

    @Override
    public Notice getNoticeDetail(int noticeId) {
        return noticeMapper.selectNoticeDetail(noticeId);
    }

    @Override
    public void writeNotice(Notice notice) {
        noticeMapper.insertNotice(notice);
    }

    @Override
    public Map<String, Object> getNoticeList(Page page) {
        Map<String, Object> result = new HashMap<>();
        List<Notice> list = noticeMapper.selectNoticeList(page);
        result.put("noticeList", list);
        PageResult pgResult = new PageResult(page.getPageNo(), noticeMapper.selectNoticeListCount(page), page.getListSize(), 10, page.getQuery());
        result.put("pageResult", pgResult);
        return result;
    }
}
