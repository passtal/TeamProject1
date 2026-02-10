package com.aloha.durudurub.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.durudurub.dto.Report;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.ReportService;
import com.aloha.durudurub.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * 신고 API 컨트롤러 (Ajax)
 */
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> reportUser(@RequestBody Report report, Principal principal) throws Exception {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        int reporterNo = userService.selectByUserId(principal.getName()).getNo();
        report.setReporterNo(reporterNo);

        // clubNo/targetNo/reason 검증은 있으면 좋음
        Map<String, Object> result = reportService.reportUser(report);
        result.put("success", true);

        return ResponseEntity.ok(result);
    }
    
}
