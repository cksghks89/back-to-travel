package com.ssafy.enjoytrip.model.dto;

import lombok.Data;

//페이징 관련 데이터 처리 클래스
@Data
public class PageResult {
    private int pageNo;
    private int count;
    private String query;

    private int tabSize;
    private int listSize;

    private int beginPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    public PageResult(int pageNo, int count, String query) {
        this(pageNo, count, 10, 10, query);
    }

    public PageResult(int pageNo, int count, int listSize, int tabSize, String query) {
        this.pageNo = pageNo;
        this.count = count;
        this.listSize = listSize;
        this.tabSize = tabSize;
        this.query = query;

        int currTab = (pageNo - 1) / tabSize + 1;
        int lastPage = (count - 1) / listSize + 1;
        this.beginPage = (currTab - 1) * tabSize + 1;
        this.endPage = (currTab * tabSize > lastPage) ? lastPage : currTab * tabSize;

        this.prev = beginPage != 1;
        this.next = endPage != lastPage;
    }

}
