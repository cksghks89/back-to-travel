package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.dto.Board;
import com.ssafy.enjoytrip.model.dto.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> selectBoardList(Page page);

    void deleteBoard(int boardId);

    void updateBoard(Board board);

    Board selectBoardDetail(int boardId);

    void insertBoard(Board board);

    int selectBoardListCount(Page page);
}
