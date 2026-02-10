package com.aloha.durudurub.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * OpenAI API 공통 호출 서비스
 * - 모든 AI 기능(검색, 챗봇, 요약, 필터링, 카테고리 추천)에서 공유
 */
@Slf4j
@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * OpenAI Chat Completions API 호출
     * @param systemPrompt AI 역할 지정 (system 메시지)
     * @param userMessage  사용자 입력 (user 메시지)
     * @param maxTokens    최대 토큰 수
     * @param temperature  창의성 (0.0 ~ 1.0)
     * @return AI 응답 텍스트
     */
    public String call(String systemPrompt, String userMessage, int maxTokens, double temperature) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> sysMsg = new HashMap<>();
        sysMsg.put("role", "system");
        sysMsg.put("content", systemPrompt);
        messages.add(sysMsg);

        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);

        requestBody.put("messages", messages);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", temperature);

        String jsonBody = objectMapper.writeValueAsString(requestBody);
        log.info("OpenAI 요청 전송 (model={}, maxTokens={})", model, maxTokens);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        JsonNode root = objectMapper.readTree(response.getBody());
        String content = root.path("choices")
                             .get(0)
                             .path("message")
                             .path("content")
                             .asText();

        log.info("OpenAI 응답: {}", content);
        return content;
    }

    /**
     * 기본값으로 호출 (maxTokens=200, temperature=0.7)
     */
    public String call(String systemPrompt, String userMessage) throws Exception {
        return call(systemPrompt, userMessage, 200, 0.7);
    }
}
