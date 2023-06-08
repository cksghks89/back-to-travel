package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.dto.Board;
import com.ssafy.enjoytrip.model.dto.FileInfo;
import com.ssafy.enjoytrip.model.dto.Page;
import com.ssafy.enjoytrip.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> getBoardList(String pageNo, String listSize, String query) {
        log.info("{} {} {}", pageNo, listSize, query);

        Page page = new Page();
        if (!("".equals(pageNo) || pageNo == null)) {
            page.setPageNo(Integer.parseInt(pageNo));
            if (!(listSize == null || "".equals(listSize))) {
                page.setListSize(10);
            }
        }
        page.setQuery(query);
        log.info("{}", page);

        Map<String, Object> result = boardService.getBoardList(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("delete/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable("boardId") int boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("modify/{boardId}")
    public ResponseEntity<?> modifyBoard(@PathVariable("boardId") int boardId, @RequestBody Board board) {
        boardService.modifyBoard(boardId, board);
        return ResponseEntity.ok().build();
    }

    @GetMapping("detail/{boardId}")
    public ResponseEntity<?> getBoardDetail(@PathVariable("boardId") int boardId) {
        return ResponseEntity.ok(boardService.getBoardDetail(boardId));
    }

    @PostMapping("write")
    public ResponseEntity<?> writeBoard(@RequestBody Board board, @RequestPart(name = "file", required = false) MultipartFile file) {
        FileInfo fileInfo = null;
        if (file != null) saveUploadedFile(file);
        boardService.writeBoard(board);
        return ResponseEntity.ok().build();
    }

    private FileInfo saveUploadedFile(MultipartFile file) {
        FileInfo fileInfo = null;
        if (!file.isEmpty()) {
            String fileSavePath = "";
            fileInfo = new FileInfo();
            try {
                File destFile = new File(fileSavePath, Objects.requireNonNull(file.getOriginalFilename()));
                file.transferTo(destFile);

                fileInfo.setSaveFolder(fileSavePath);
                fileInfo.setOriginalFile(file.getOriginalFilename());
                fileInfo.setSaveFile(destFile.getName());
            } catch (IOException e) {
                log.error("Failed to save uploaded file:{}", e.getMessage());
            }
        }
        return fileInfo;
    }
}
