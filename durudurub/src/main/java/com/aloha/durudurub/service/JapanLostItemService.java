package com.aloha.durudurub.service;

/**
 * 일본 분실물 신고서(遺失届) 작성 보조 서비스
 * - 한국어로 입력된 분실 정보를 바탕으로 일본어 遺失届 양식을 생성
 */
public interface JapanLostItemService {

    /**
     * 분실물 신고 정보를 바탕으로 일본어 遺失届 양식 내용을 생성
     *
     * @param itemName      분실한 물건 이름 (한국어)
     * @param lostDate      분실 날짜 (예: 2024-01-15)
     * @param lostTime      분실 시간 (예: 14:30)
     * @param lostPlace     분실 장소 (한국어)
     * @param description   물건 설명 (색상, 브랜드, 특징 등 - 한국어)
     * @param reporterName  신고자 이름
     * @param contact       연락처
     * @return 생성된 遺失届 내용 (일본어)
     */
    String generate(String itemName, String lostDate, String lostTime,
                    String lostPlace, String description,
                    String reporterName, String contact) throws Exception;
}
