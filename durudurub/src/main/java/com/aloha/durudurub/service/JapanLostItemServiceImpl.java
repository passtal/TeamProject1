package com.aloha.durudurub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 일본 분실물 신고서(遺失届) 작성 보조 서비스 구현체
 * - 한국어 입력 정보를 기반으로 OpenAI를 통해 일본어 遺失届 양식을 생성
 */
@Slf4j
@Service
public class JapanLostItemServiceImpl implements JapanLostItemService {

    @Autowired
    private OpenAiService openAiService;

    @Override
    public String generate(String itemName, String lostDate, String lostTime,
                           String lostPlace, String description,
                           String reporterName, String contact) throws Exception {

        String systemPrompt =
            "あなたは日本語の遺失届作成を支援するアシスタントです。\n" +
            "ユーザーが提供する韓国語の情報をもとに、日本の交番（警察署）に提出できる遺失届の記入内容を日本語で生成してください。\n\n" +
            "## 出力形式\n" +
            "以下の形式で出力してください。各項目を明確に分けて記入してください：\n\n" +
            "【遺失届】\n\n" +
            "■ 届出人（신고자 정보）\n" +
            "氏名（이름）：＿＿＿＿＿＿＿＿\n" +
            "住所（주소）：現在、日本に旅行中\n" +
            "連絡先（연락처）：＿＿＿＿＿＿＿＿\n\n" +
            "■ 遺失品（분실물）\n" +
            "品名（품명）：＿＿＿＿＿＿＿＿\n" +
            "特徴（특징）：＿＿＿＿＿＿＿＿\n\n" +
            "■ 遺失年月日（분실 날짜）：＿＿＿＿年＿＿月＿＿日　＿＿時頃\n\n" +
            "■ 遺失場所（분실 장소）：＿＿＿＿＿＿＿＿\n\n" +
            "■ 遺失した状況（분실 경위）：＿＿＿＿＿＿＿＿\n\n" +
            "## 注意事項\n" +
            "- 不明な情報は「不明」または「わかりません」と記入してください\n" +
            "- 届出人の住所欄には「韓国からの旅行者」と明記してください\n" +
            "- 日本語で丁寧かつ正確に記入してください\n" +
            "- 出力には、交番の警察官が理解しやすいよう日本語の遺失届フォームのみを含めてください\n" +
            "- フォームの下に、韓国語の簡単な説明（このフォームの使い方）も追加してください";

        String userMessage = String.format(
            "다음 정보를 바탕으로 遺失届를 작성해주세요:\n\n" +
            "분실한 물건: %s\n" +
            "분실 날짜: %s\n" +
            "분실 시간: %s\n" +
            "분실 장소: %s\n" +
            "물건 설명 (색상/특징/브랜드 등): %s\n" +
            "신고자 이름: %s\n" +
            "연락처: %s",
            itemName,
            lostDate != null && !lostDate.trim().isEmpty() ? lostDate : "불명",
            lostTime != null && !lostTime.trim().isEmpty() ? lostTime : "불명",
            lostPlace,
            description != null && !description.trim().isEmpty() ? description : "상세 정보 없음",
            reporterName != null && !reporterName.trim().isEmpty() ? reporterName : "미입력",
            contact != null && !contact.trim().isEmpty() ? contact : "미입력"
        );

        log.info("遺失届 생성 요청 - 물건: {}, 장소: {}", itemName, lostPlace);
        return openAiService.call(systemPrompt, userMessage, 1000, 0.3);
    }
}
