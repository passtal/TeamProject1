package com.aloha.durudurub.service;

import java.util.List;

import com.aloha.durudurub.dto.Club;

/**
 * AI 챗봇 상담 서비스 (모임 추천 대화형)
 */
public interface AiChatbotService {

    /**
     * 사용자 메시지에 대해 모임 추천 대화 응답을 생성
     * @param userMessage 사용자 입력
     * @param clubs       현재 DB에 있는 모임 목록 (참고용)
     * @return AI 대화 응답
     */
    String chat(String userMessage, List<Club> clubs) throws Exception;
}
