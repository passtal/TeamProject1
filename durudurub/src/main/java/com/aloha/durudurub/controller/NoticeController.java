package com.aloha.durudurub.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.durudurub.dto.Notice;
import com.aloha.durudurub.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 공지사항 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    
    private final NoticeService noticeService;

    // 목록
    @GetMapping("")
    public String getNoticeList(
        Model model
    ) {
        List<Notice> noticeList = noticeService.getNoticeList();
        model.addAttribute("noticeList", noticeList);

        log.info("*****noticeList: {} ", noticeList);
        return "notice/list";
    }
    
    // 상세보기
    @GetMapping("/{noticeNo}")
    public String getNotice(
        @PathVariable("noticeNo") int noticeNo,
        Model model
    ) {
        Notice notice = noticeService.getNoticeAndIncrease(noticeNo);
        model.addAttribute("notice", notice);

        log.info("*****notice: {} ", notice);

        return "notice/detail";
    }
    // 상세보기 - api
    @GetMapping("/api/{noticeNo}")
    @ResponseBody
    public Notice getNoticeDetail(@PathVariable int noticeNo) {
        return noticeService.getNotice(noticeNo);
    }
    
}
