package com.aloha.durudurub.service;

import com.aloha.durudurub.dto.AiSearchResponse;

public interface AiSearchService {

    // AI 검색 실행
    AiSearchResponse search(String userMessage) throws Exception;
}
