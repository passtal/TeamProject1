package com.aloha.durudurub.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.durudurub.dto.Notice;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 공지사항 컨트롤러
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
    
    // 목록
    @GetMapping("")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    // 상세보기
    @GetMapping("/{noticeNo}")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
