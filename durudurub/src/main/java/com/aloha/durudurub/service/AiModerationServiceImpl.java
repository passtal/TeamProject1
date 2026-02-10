package com.aloha.durudurub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * AI 부적절 콘텐츠 감지 서비스 구현체
 * - 댓글, 게시글 작성 시 욕설/혐오/음란/폭력/광고 자동 감지
 * - JSON 형태로 응답받아 파싱
 */
@Slf4j
@Service
public class AiModerationServiceImpl implements AiModerationService {

    @Autowired
    private OpenAiService openAiService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModerationResult moderate(String text) throws Exception {
        if (text == null || text.trim().isEmpty()) {
            return new ModerationResult(false, "정상", "빈 텍스트", 1.0);
        }

        String systemPrompt =
            "너는 한국어 커뮤니티 콘텐츠 검수 봇이야.\n" +
            "아래 텍스트가 부적절한지 판단해줘.\n" +
            "판단 기준: 욕설, 비속어, 혐오발언, 차별발언, 음란물, 폭력적 내용, 스팸/광고\n\n" +
            "반드시 아래 JSON 형식으로만 답변해. 다른 텍스트 없이 JSON만 출력해:\n" +
            "{\n" +
            "  \"flagged\": true 또는 false,\n" +
            "  \"category\": \"욕설\" 또는 \"혐오\" 또는 \"음란\" 또는 \"폭력\" 또는 \"광고\" 또는 \"정상\",\n" +
            "  \"reason\": \"판단 사유를 한 줄로\",\n" +
            "  \"confidence\": 0.0~1.0 사이의 확신도\n" +
            "}";

        String response = openAiService.call(systemPrompt, text, 150, 0.1);
        log.info("악플 필터링 결과: {}", response);

        try {
            // JSON 응답 파싱
            JsonNode node = objectMapper.readTree(response);
            boolean flagged = node.path("flagged").asBoolean(false);
            String category = node.path("category").asText("정상");
            String reason = node.path("reason").asText("");
            double confidence = node.path("confidence").asDouble(0.5);

            return new ModerationResult(flagged, category, reason, confidence);
        } catch (Exception e) {
            log.warn("악플 필터링 JSON 파싱 실패, 원문 응답: {}", response);
            // 파싱 실패 시 안전하게 통과 처리
            return new ModerationResult(false, "정상", "AI 응답 파싱 실패", 0.0);
        }
    }
}
