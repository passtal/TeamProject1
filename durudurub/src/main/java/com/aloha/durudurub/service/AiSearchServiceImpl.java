package com.aloha.durudurub.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aloha.durudurub.dao.ClubMapper;
import com.aloha.durudurub.dto.AiSearchResponse;
import com.aloha.durudurub.dto.Club;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AiSearchServiceImpl implements AiSearchService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.model}")
    private String model;

    @Autowired
    private ClubMapper clubMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    // Jackson ObjectMapper (Spring Boot 내장, Java 23 완벽 지원)
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AiSearchResponse search(String userMessage) throws Exception {

        // 1단계: OpenAI에게 사용자 메시지에서 검색 키워드를 추출하도록 요청
        String keyword = extractKeyword(userMessage);
        log.info("AI가 추출한 키워드: {}", keyword);

        // 2단계: 추출된 키워드로 DB 검색
        List<Club> clubs = clubMapper.search(keyword);
        log.info("검색 결과: {}건", clubs.size());

        // 3단계: 검색 결과를 바탕으로 AI에게 추천 메시지 생성 요청
        String aiMessage = generateRecommendation(userMessage, clubs);

        // 4단계: 응답 DTO 조립
        return AiSearchResponse.builder()
                .aiMessage(aiMessage)
                .clubs(clubs)
                .keyword(keyword)
                .build();
    }

    /**
     * OpenAI API를 호출하여 사용자 메시지에서 검색 키워드를 추출
     */
    private String extractKeyword(String userMessage) throws Exception {
        String systemPrompt =
            "너는 모임 검색 도우미야. " +
            "사용자의 메시지에서 모임 검색에 사용할 핵심 키워드를 1~2개 추출해줘. " +
            "키워드만 공백으로 구분해서 답해줘. " +
            "예시: '서울에서 등산 같이 할 사람?' → '서울 등산'";

        return callOpenAI(systemPrompt, userMessage).trim();
    }

    /**
     * 검색 결과를 기반으로 AI 추천 메시지 생성
     */
    private String generateRecommendation(String userMessage, List<Club> clubs) throws Exception {
        if (clubs.isEmpty()) {
            return "아쉽게도 관련 모임을 찾지 못했어요. 다른 키워드로 검색해보세요!";
        }

        StringBuilder clubInfo = new StringBuilder();
        for (Club club : clubs) {
            clubInfo.append(String.format("- %s (%s)\n",
                club.getTitle(),
                club.getLocation()
            ));
        }

        String systemPrompt =
            "너는 친근한 모임 추천 도우미야. " +
            "아래 검색 결과를 참고해서 2~3줄로 짧게 추천해줘.\n\n" +
            "[검색된 모임]\n" + clubInfo.toString();

        return callOpenAI(systemPrompt, userMessage);
    }

    /**
     * OpenAI API HTTP 호출 (핵심 메서드)
     * Java Map → Jackson으로 JSON 변환 → HTTP POST 전송 → JSON 응답 파싱
     */
    private String callOpenAI(String systemPrompt, String userMessage) throws Exception {

        // 1. HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);    // Authorization: Bearer sk-xxx...

        // 2. 요청 본문(JSON) 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);

        // messages 배열 구성
        List<Map<String, String>> messages = new ArrayList<>();

        // system 메시지: AI의 역할 지정
        Map<String, String> sysMsg = new HashMap<>();
        sysMsg.put("role", "system");
        sysMsg.put("content", systemPrompt);
        messages.add(sysMsg);

        // user 메시지: 사용자 입력
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);

        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 200);
        requestBody.put("temperature", 0.7);

        // 3. Map → JSON 문자열 변환 (Jackson)
        String jsonBody = objectMapper.writeValueAsString(requestBody);
        log.info("OpenAI 요청 전송");

        // 4. HTTP POST 요청 전송
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        // 5. JSON 응답 파싱 (Jackson)
        JsonNode root = objectMapper.readTree(response.getBody());
        String content = root.path("choices")
                             .get(0)
                             .path("message")
                             .path("content")
                             .asText();

        log.info("OpenAI 응답: {}", content);
        return content;
    }
}
