package com.aloha.durudurub.service;

/**
 * AI 게시글 자동 요약 서비스
 */
public interface AiSummaryService {

    /**
     * 게시글 내용을 1~2줄로 자동 요약
     * @param title   게시글 제목
     * @param content 게시글 본문
     * @return 요약된 텍스트
     */
    String summarize(String title, String content) throws Exception;
}
