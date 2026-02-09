package com.aloha.durudurub.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiSearchResponse {
    private String aiMessage;       // AI가 생성한 추천 메시지
    private List<Club> clubs;       // 검색된 모임 목록
    private String keyword;         // AI가 추출한 검색 키워드
    private Integer remaining;      // 남은 무료 횟수 (null = 무제한)
}
