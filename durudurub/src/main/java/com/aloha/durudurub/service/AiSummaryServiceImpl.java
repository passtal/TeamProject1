package com.aloha.durudurub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * AI 게시글 자동 요약 서비스 구현체
 * - 게시글이나 모임 설명이 길 때 1~2줄로 핵심만 요약
 */
@Slf4j
@Service
public class AiSummaryServiceImpl implements AiSummaryService {

    @Autowired
    private OpenAiService openAiService;

    @Override
    public String summarize(String title, String content) throws Exception {
        if (content == null || content.trim().isEmpty()) {
            return "내용이 없습니다.";
        }

        // 너무 긴 본문은 앞부분만 전달 (토큰 절약)
        String truncated = content.length() > 1500
            ? content.substring(0, 1500) + "..."
            : content;

        String systemPrompt =
            "너는 게시글 요약 도우미야.\n" +
            "아래 게시글의 핵심 내용을 한국어로 1~2문장(50자 이내)으로 요약해줘.\n" +
            "불필요한 수식어 없이 핵심만 간결하게 답변해줘.";

        String userMessage = String.format("제목: %s\n본문: %s", title, truncated);

        log.info("게시글 요약 요청 - 제목: {}", title);
        return openAiService.call(systemPrompt, userMessage, 100, 0.3);
    }
}
