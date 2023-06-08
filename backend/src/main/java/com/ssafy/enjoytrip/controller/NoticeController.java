package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.dto.FileInfo;
import com.ssafy.enjoytrip.model.dto.Notice;
import com.ssafy.enjoytrip.model.dto.Page;
import com.ssafy.enjoytrip.model.service.NoticeService;
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
@RequestMapping("/notice")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/list")
    public ResponseEntity<?> getNoticeList(String pageNo, String listSize, String query) {
        Page page = new Page();
        if (!("".equals(pageNo) || pageNo == null)) {
            page.setPageNo(Integer.parseInt(pageNo));
            if (!(listSize == null || "".equals(listSize))) {
                page.setListSize(10);
            }
        }
        page.setQuery(query);
        log.info("{}", page);

        Map<String, Object> result = noticeService.getNoticeList(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("delete/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable("noticeId") int noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("modify/{noticeId}")
    public ResponseEntity<?> modifyNotice(@PathVariable("noticeId") int noticeId, @RequestBody Notice notice) {
        noticeService.modifyNotice(noticeId, notice);
        return ResponseEntity.ok().build();
    }

    @GetMapping("detail/{noticeId}")
    public ResponseEntity<?> getNoticeDetail(@PathVariable("noticeId") int noticeId) {
        return ResponseEntity.ok(noticeService.getNoticeDetail(noticeId));
    }

    @PostMapping("write")
    public ResponseEntity<?> writeNotice(@RequestBody Notice notice, @RequestPart(name = "file", required = false) MultipartFile file) {
        FileInfo fileInfo = null;
        if (file != null) saveUploadedFile(file);
        noticeService.writeNotice(notice);
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
