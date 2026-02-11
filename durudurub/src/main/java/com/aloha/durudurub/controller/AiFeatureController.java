package com.aloha.durudurub.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.durudurub.service.AiCategoryService;
import com.aloha.durudurub.service.AiCategoryService.CategoryRecommendation;
import com.aloha.durudurub.service.AiChatbotService;
import com.aloha.durudurub.service.AiModerationService;
import com.aloha.durudurub.service.AiModerationService.ModerationResult;
import com.aloha.durudurub.service.AiSummaryService;
import com.aloha.durudurub.service.ClubService;

import lombok.extern.slf4j.Slf4j;

/**
 * AI 기능 통합 컨트롤러
 * - 챗봇 상담, 게시글 요약, 악플 필터링, 카테고리 추천
 * - 모든 엔드포인트는 /api/ai/** 아래에 위치 (CSRF 면제)
 */
@Slf4j
@RestController
@RequestMapping("/api/ai")
public class AiFeatureController {

    @Autowired
    private AiChatbotService aiChatbotService;

    @Autowired
    private AiSummaryService aiSummaryService;

    @Autowired
    private AiModerationService aiModerationService;

    @Autowired
    private AiCategoryService aiCategoryService;

    @Autowired
    private ClubService clubService;

    // ========================================================
    // 1. 챗봇 상담 - 모임 추천 대화
    // ========================================================
    /**
     * POST /api/ai/chat
     * body: { "message": "등산 좋아하는데 추천해줘" }
     * response: { "reply": "..." }
     */
    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody Map<String, String> request) {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }

        String message = request.get("message");
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "메시지를 입력해주세요."));
        }

        try {
            // 전체 클럽 목록을 가져와서 챗봇 컨텍스트로 제공
            var clubs = clubService.list();
            String reply = aiChatbotService.chat(message, clubs);
            return ResponseEntity.ok(Map.of("reply", reply));
        } catch (Exception e) {
            log.error("챗봇 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "챗봇 응답 중 오류가 발생했습니다."));
        }
    }

    // ========================================================
    // 2. 게시글 자동 요약
    // ========================================================
    /**
     * POST /api/ai/summarize
     * body: { "title": "게시글 제목", "content": "게시글 본문..." }
     * response: { "summary": "..." }
     */
    @PostMapping("/summarize")
    public ResponseEntity<?> summarize(@RequestBody Map<String, String> request) {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }

        String title = request.get("title");
        String content = request.get("content");

        if ((title == null || title.trim().isEmpty()) && (content == null || content.trim().isEmpty())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "요약할 내용을 입력해주세요."));
        }

        try {
            String summary = aiSummaryService.summarize(title, content);
            return ResponseEntity.ok(Map.of("summary", summary));
        } catch (Exception e) {
            log.error("요약 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "요약 중 오류가 발생했습니다."));
        }
    }

    // ========================================================
    // 3. 악플 필터링 (부적절 콘텐츠 감지)
    // ========================================================
    /**
     * POST /api/ai/moderate
     * body: { "text": "검사할 텍스트" }
     * response: { "flagged": true/false, "category": "...", "reason": "...", "confidence": 0.95 }
     */
    @PostMapping("/moderate")
    public ResponseEntity<?> moderate(@RequestBody Map<String, String> request) {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }

        String text = request.get("text");
        if (text == null || text.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "검사할 텍스트를 입력해주세요."));
        }

        try {
            ModerationResult result = aiModerationService.moderate(text);
            return ResponseEntity.ok(Map.of(
                    "flagged", result.flagged(),
                    "category", result.category(),
                    "reason", result.reason(),
                    "confidence", result.confidence()
            ));
        } catch (Exception e) {
            log.error("콘텐츠 검수 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "콘텐츠 검수 중 오류가 발생했습니다."));
        }
    }

    // ========================================================
    // 4. 카테고리 자동 추천
    // ========================================================
    /**
     * POST /api/ai/category
     * body: { "title": "모임 이름", "description": "모임 설명" }
     * response: { "categoryNo": 1, "categoryName": "...", "subCategoryNo": 2, "subCategoryName": "...", "reason": "..." }
     */
    @PostMapping("/category")
    public ResponseEntity<?> recommendCategory(@RequestBody Map<String, String> request) {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }

        String title = request.get("title");
        String description = request.get("description");

        if ((title == null || title.trim().isEmpty()) && (description == null || description.trim().isEmpty())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "제목이나 설명을 입력해주세요."));
        }

        try {
            CategoryRecommendation rec = aiCategoryService.recommend(title, description);
            return ResponseEntity.ok(Map.of(
                    "categoryNo", rec.categoryNo(),
                    "categoryName", rec.categoryName(),
                    "subCategoryNo", rec.subCategoryNo(),
                    "subCategoryName", rec.subCategoryName(),
                    "reason", rec.reason()
            ));
        } catch (Exception e) {
            log.error("카테고리 추천 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "카테고리 추천 중 오류가 발생했습니다."));
        }
    }

    // ========================================================
    // 유틸리티
    // ========================================================
    private boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null
                && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal());
    }
}
