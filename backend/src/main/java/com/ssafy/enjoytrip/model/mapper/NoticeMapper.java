package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.dto.Notice;
import com.ssafy.enjoytrip.model.dto.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<Notice> selectNoticeList(Page page);

    void deleteNotice(int noticeId);

    void updateNotice(Notice notice);

    Notice selectNoticeDetail(int noticeId);

    void insertNotice(Notice notice);

    int selectNoticeListCount(Page page);
}
