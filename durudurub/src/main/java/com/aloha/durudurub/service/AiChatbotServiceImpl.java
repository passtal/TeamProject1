package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.durudurub.dao.ClubMapper;
import com.aloha.durudurub.dto.Club;

import lombok.extern.slf4j.Slf4j;

/**
 * AI 챗봇 상담 서비스 구현체
 * - 사용자의 자연어 질문에 대해 모임 추천 대화를 수행
 * - DB의 실제 모임 데이터를 참고하여 답변
 */
@Slf4j
@Service
public class AiChatbotServiceImpl implements AiChatbotService {

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private ClubMapper clubMapper;

    @Override
    public String chat(String userMessage, List<Club> clubs) throws Exception {
        // 모임 목록이 없으면 DB에서 최신 모임을 가져와서 참고
        if (clubs == null || clubs.isEmpty()) {
            clubs = clubMapper.search("");  // 전체 모임 조회
        }

        // 모임 정보를 텍스트로 정리
        StringBuilder clubInfo = new StringBuilder();
        int count = 0;
        for (Club club : clubs) {
            if (count >= 20) break;  // 최대 20개까지만 참고
            clubInfo.append(String.format("- [%d] %s (위치: %s, 카테고리: %s, 현재인원: %d/%d)\n",
                club.getNo(),
                club.getTitle(),
                club.getLocation() != null ? club.getLocation() : "미정",
                club.getCategory() != null ? club.getCategory().getName() : "미분류",
                club.getCurrentMembers(),
                club.getMaxMembers()
            ));
            count++;
        }

        String systemPrompt =
            "너는 '두루두룹'이라는 모임 플랫폼의 친근한 상담 챗봇이야.\n" +
            "사용자가 모임에 대해 질문하면, 아래 실제 모임 목록을 참고해서 추천하거나 안내해줘.\n" +
            "답변은 3~5줄 이내로 짧고 친근하게 해줘.\n" +
            "모임이 없는 경우 직접 모임을 만들어보라고 안내해줘.\n\n" +
            "[현재 운영 중인 모임 목록]\n" + clubInfo.toString();

        return openAiService.call(systemPrompt, userMessage, 300, 0.8);
    }
}
