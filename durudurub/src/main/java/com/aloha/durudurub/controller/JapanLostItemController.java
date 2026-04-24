package com.aloha.durudurub.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.durudurub.service.JapanLostItemService;

import lombok.extern.slf4j.Slf4j;

/**
 * 일본 분실물 신고서(遺失届) 작성 보조 컨트롤러
 * - GET  /japan-lost-item        : 입력 폼 페이지
 * - POST /api/ai/japan-lost-item : AI 생성 API
 */
@Slf4j
@Controller
@RequestMapping
public class JapanLostItemController {

    @Autowired
    private JapanLostItemService japanLostItemService;

    /**
     * 遺失届 작성 보조 페이지
     */
    @GetMapping("/japan-lost-item")
    public String form() {
        return "japan-lost-item/form";
    }

    /**
     * POST /api/ai/japan-lost-item
     * body: {
     *   "itemName": "지갑",
     *   "lostDate": "2024-01-15",
     *   "lostTime": "14:30",
     *   "lostPlace": "도쿄 시부야역 근처",
     *   "description": "갈색 가죽 지갑, 삼성카드 포함",
     *   "reporterName": "홍길동",
     *   "contact": "+82-10-1234-5678"
     * }
     * response: { "result": "【遺失届】..." }
     */
    @PostMapping("/api/ai/japan-lost-item")
    @ResponseBody
    public ResponseEntity<?> generate(@RequestBody Map<String, String> request) {
        String itemName = request.get("itemName");
        String lostPlace = request.get("lostPlace");

        if (itemName == null || itemName.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "분실한 물건 이름을 입력해주세요."));
        }
        if (lostPlace == null || lostPlace.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "분실 장소를 입력해주세요."));
        }

        try {
            String result = japanLostItemService.generate(
                    itemName,
                    request.get("lostDate"),
                    request.get("lostTime"),
                    lostPlace,
                    request.get("description"),
                    request.get("reporterName"),
                    request.get("contact")
            );
            return ResponseEntity.ok(Map.of("result", result));
        } catch (Exception e) {
            log.error("遺失届 생성 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "遺失届 생성 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요."));
        }
    }
}
