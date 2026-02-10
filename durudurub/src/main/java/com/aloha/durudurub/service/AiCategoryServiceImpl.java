package com.aloha.durudurub.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.durudurub.dto.Category;
import com.aloha.durudurub.dto.SubCategory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * AI 카테고리 자동 추천 서비스 구현체
 * - DB 카테고리 전체 목록을 프롬프트에 포함하여 AI에게 가장 적합한 카테고리를 추천받음
 */
@Slf4j
@Service
public class AiCategoryServiceImpl implements AiCategoryService {

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private CategoryService categoryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CategoryRecommendation recommend(String title, String description) throws Exception {
        // 1. DB에서 전체 카테고리 + 서브카테고리 목록 조회
        List<Category> categories = categoryService.list();

        // 2. 카테고리 목록을 문자열로 구성
        StringBuilder categoryInfo = new StringBuilder();
        for (Category cat : categories) {
            categoryInfo.append(String.format("- [%d] %s: %s\n", cat.getNo(), cat.getName(),
                    cat.getDescription() != null ? cat.getDescription() : ""));

            if (cat.getSubCategoryList() != null) {
                for (SubCategory sub : cat.getSubCategoryList()) {
                    categoryInfo.append(String.format("  - [%d] %s\n", sub.getNo(), sub.getName()));
                }
            }
        }

        // 3. 시스템 프롬프트 구성
        String systemPrompt =
            "너는 모임/클럽 카테고리 분류 전문가야.\n" +
            "아래 카테고리 목록 중에서 가장 적합한 카테고리를 추천해줘.\n" +
            "반드시 목록에 있는 카테고리만 추천해야 해.\n\n" +
            "=== 카테고리 목록 ===\n" +
            categoryInfo.toString() + "\n" +
            "반드시 아래 JSON 형식으로만 답변해. 다른 텍스트 없이 JSON만 출력해:\n" +
            "{\n" +
            "  \"categoryNo\": 메인 카테고리 번호(숫자),\n" +
            "  \"categoryName\": \"메인 카테고리명\",\n" +
            "  \"subCategoryNo\": 서브 카테고리 번호(숫자, 없으면 0),\n" +
            "  \"subCategoryName\": \"서브 카테고리명(없으면 빈 문자열)\",\n" +
            "  \"reason\": \"추천 사유를 한 줄로\"\n" +
            "}";

        // 4. 사용자 메시지 구성
        String userMessage = String.format("제목: %s\n설명: %s",
                title != null ? title : "",
                description != null ? description.substring(0, Math.min(description.length(), 1000)) : "");

        // 5. OpenAI API 호출
        String response = openAiService.call(systemPrompt, userMessage, 200, 0.2);
        log.info("카테고리 추천 결과: {}", response);

        // 6. JSON 파싱
        try {
            JsonNode node = objectMapper.readTree(response);
            int categoryNo = node.path("categoryNo").asInt(0);
            String categoryName = node.path("categoryName").asText("");
            int subCategoryNo = node.path("subCategoryNo").asInt(0);
            String subCategoryName = node.path("subCategoryName").asText("");
            String reason = node.path("reason").asText("");

            return new CategoryRecommendation(categoryNo, categoryName, subCategoryNo, subCategoryName, reason);
        } catch (Exception e) {
            log.warn("카테고리 추천 JSON 파싱 실패, 원문: {}", response);
            // 파싱 실패 시 기본값
            return new CategoryRecommendation(0, "", 0, "", "AI 응답 파싱 실패");
        }
    }
}
