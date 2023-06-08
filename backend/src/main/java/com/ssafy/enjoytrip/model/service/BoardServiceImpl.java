package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.Board;
import com.ssafy.enjoytrip.model.dto.Page;
import com.ssafy.enjoytrip.model.dto.PageResult;
import com.ssafy.enjoytrip.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;


    @Override
    public void deleteBoard(int boardId) {
        boardMapper.deleteBoard(boardId);
    }

    public void modifyBoard(int boardId, Board board) {
        board.setBoardId(boardId);
        boardMapper.updateBoard(board);
    }

    @Override
    public Board getBoardDetail(int boardId) {
        return boardMapper.selectBoardDetail(boardId);
    }

    @Override
    public void writeBoard(Board board) {
        boardMapper.insertBoard(board);
    }

    @Override
    public Map<String, Object> getBoardList(Page page) {
        Map<String, Object> result = new HashMap<>();
        List<Board> list = boardMapper.selectBoardList(page);
        result.put("boardList", list);
        PageResult pgResult = new PageResult(page.getPageNo(), boardMapper.selectBoardListCount(page), page.getListSize(), 10, page.getQuery());
        result.put("pageResult", pgResult);
        return result;
    }
}
