package com.aloha.durudurub.service;

/**
 * AI 카테고리 자동 추천 서비스
 * - 모임 제목/설명을 분석하여 적절한 카테고리를 추천
 */
public interface AiCategoryService {

    /**
     * 카테고리 추천 결과
     * @param categoryNo    추천 메인 카테고리 번호
     * @param categoryName  메인 카테고리명
     * @param subCategoryNo 추천 서브 카테고리 번호 (없으면 0)
     * @param subCategoryName 서브 카테고리명
     * @param reason        추천 사유
     */
    record CategoryRecommendation(
        int categoryNo,
        String categoryName,
        int subCategoryNo,
        String subCategoryName,
        String reason
    ) {}

    /**
     * 제목과 설명으로 적절한 카테고리 추천
     * @param title       모임/게시글 제목
     * @param description 모임/게시글 설명
     * @return 추천 카테고리 결과
     */
    CategoryRecommendation recommend(String title, String description) throws Exception;
}
