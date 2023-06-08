package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.Board;
import com.ssafy.enjoytrip.model.dto.Page;

import java.util.Map;

public interface BoardService {

    void deleteBoard(int boardId);

    void modifyBoard(int boardId, Board board);

    Board getBoardDetail(int boardId);

    void writeBoard(Board board);

    Map<String, Object> getBoardList(Page page);
}
