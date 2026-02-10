package com.aloha.durudurub.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.durudurub.dao.AiSearchLogMapper;
import com.aloha.durudurub.dto.AiSearchLog;
import com.aloha.durudurub.dto.AiSearchResponse;
import com.aloha.durudurub.dto.Subscription;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.AiSearchService;
import com.aloha.durudurub.service.SubscriptionService;
import com.aloha.durudurub.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/ai")
public class AiSearchController {

    private static final int FREE_LIMIT = 3;   // 무료 검색 횟수

    @Autowired
    private AiSearchService aiSearchService;

    @Autowired
    private UserService userService;

    @Autowired
    private AiSearchLogMapper aiSearchLogMapper;

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * AI 검색 상태 확인 API
     */
    @GetMapping("/status")
    public ResponseEntity<?> status() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> result = new HashMap<>();

        boolean loggedIn = auth != null
                && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal());

        result.put("loggedIn", loggedIn);

        if (!loggedIn) {
            result.put("canSearch", false);
            result.put("remaining", 0);
            return ResponseEntity.ok(result);
        }

        // 관리자는 무제한
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            result.put("canSearch", true);
            result.put("remaining", -1);
            return ResponseEntity.ok(result);
        }

        // 일반 유저: 구독 상태 확인
        User user = userService.selectByUserId(auth.getName());
        Subscription sub = subscriptionService.selectByUserNo(user.getNo());
        if (sub != null && "ACTIVE".equals(sub.getStatus())) {
            // 구독자는 무제한
            result.put("canSearch", true);
            result.put("remaining", -1);
            return ResponseEntity.ok(result);
        }

        // 비구독자: 무료 횟수 제한
        int totalCount = aiSearchLogMapper.countByUserNo(user.getNo());
        int remaining = FREE_LIMIT - totalCount;

        result.put("canSearch", remaining > 0);
        result.put("remaining", Math.max(remaining, 0));

        return ResponseEntity.ok(result);
    }

    /**
     * AI 검색 실행 API
     */
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody Map<String, String> request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean loggedIn = auth != null
                && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal());

        if (!loggedIn) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }

        User user = userService.selectByUserId(auth.getName());
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // 관리자가 아니면 횟수 체크
        if (!isAdmin) {
            // 구독자는 무제한
            Subscription sub = subscriptionService.selectByUserNo(user.getNo());
            boolean isSubscriber = sub != null && "ACTIVE".equals(sub.getStatus());

            if (!isSubscriber) {
                int totalCount = aiSearchLogMapper.countByUserNo(user.getNo());
                if (totalCount >= FREE_LIMIT) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(Map.of("error", "무료 검색 횟수를 모두 사용하셨습니다."));
                }
            }
        }

        String userMessage = request.get("message");
        log.info("AI 검색 요청 (user: {}): {}", user.getUserId(), userMessage);

        try {
            AiSearchResponse response = aiSearchService.search(userMessage);

            // 검색 로그 저장 (횟수 차감)
            AiSearchLog searchLog = AiSearchLog.builder()
                    .userNo(user.getNo())
                    .keyword(response.getKeyword())
                    .userMessage(userMessage)
                    .resultCount(response.getClubs() != null ? response.getClubs().size() : 0)
                    .build();
            aiSearchLogMapper.insert(searchLog);

            // 남은 횟수 계산하여 응답에 포함
            if (!isAdmin) {
                Subscription sub2 = subscriptionService.selectByUserNo(user.getNo());
                boolean isSubscriber = sub2 != null && "ACTIVE".equals(sub2.getStatus());
                if (isSubscriber) {
                    response.setRemaining(-1);
                } else {
                    int newTotalCount = aiSearchLogMapper.countByUserNo(user.getNo());
                    int remaining = Math.max(FREE_LIMIT - newTotalCount, 0);
                    response.setRemaining(remaining);
                }
            } else {
                response.setRemaining(-1);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("AI 검색 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "AI 검색 중 오류가 발생했습니다."));
        }
    }
}
