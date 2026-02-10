package com.aloha.durudurub.service;

/**
 * AI 부적절 콘텐츠 감지 서비스 (악플 필터링)
 */
public interface AiModerationService {

    /**
     * 텍스트에서 부적절한 내용(욕설, 혐오, 음란, 폭력, 광고 등)을 감지
     * @param text 검사할 텍스트 (댓글, 게시글 등)
     * @return 감지 결과
     */
    ModerationResult moderate(String text) throws Exception;

    /**
     * 감지 결과 DTO
     */
    record ModerationResult(
        boolean flagged,        // true면 부적절 콘텐츠
        String category,       // 감지 카테고리 (욕설, 혐오, 음란, 폭력, 광고, 정상)
        String reason,         // 판단 사유
        double confidence      // 확신도 (0.0 ~ 1.0)
    ) {}
}
