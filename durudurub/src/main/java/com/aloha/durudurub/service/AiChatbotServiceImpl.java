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

        // 모임 정보를 텍스트로 정리 (설명 포함)
        StringBuilder clubInfo = new StringBuilder();
        int count = 0;
        for (Club club : clubs) {
            if (count >= 30) break;  // 최대 30개까지 참고
            String desc = club.getDescription() != null
                ? club.getDescription().substring(0, Math.min(club.getDescription().length(), 80))
                : "";
            clubInfo.append(String.format("- [%d] %s | %s | 위치: %s | 카테고리: %s | 인원: %d/%d | 설명: %s\n",
                club.getNo(),
                club.getTitle(),
                club.getStatus() != null ? club.getStatus() : "",
                club.getLocation() != null ? club.getLocation() : "미정",
                club.getCategory() != null ? club.getCategory().getName() : "미분류",
                club.getCurrentMembers(),
                club.getMaxMembers(),
                desc
            ));
            count++;
        }

        String systemPrompt =
            "너는 '두루두룹'이라는 모임/소셜 플랫폼의 친근하고 유능한 AI 어시스턴트야.\n" +
            "이름은 '두루'라고 해.\n\n" +
            "## 너의 역할\n" +
            "1. 사용자와 자연스럽게 대화하면서 모임을 추천하거나 안내해줘.\n" +
            "2. 사용자의 취향, 관심사, 위치 등을 파악해서 맞춤 추천을 해줘.\n" +
            "3. 모임 관련 질문이 아니더라도 친절하게 대화해줘.\n" +
            "4. 플랫폼 사용법이나 기능에 대한 질문에도 안내해줘.\n\n" +
            "## 대화 스타일\n" +
            "- 반말/존댓말: 사용자의 말투에 맞춰줘\n" +
            "- 이모지를 적절히 사용해서 친근하게 대화해줘\n" +
            "- 추천할 때는 왜 그 모임이 적합한지 이유도 함께 설명해줘\n" +
            "- 모임이 없으면 직접 만들어보라고 자연스럽게 안내해줘\n" +
            "- 여러 모임을 추천할 때는 번호를 매겨서 보기 좋게 정리해줘\n\n" +
            "## 참고할 수 있는 실제 모임 데이터\n" +
            clubInfo.toString();

        return openAiService.call(systemPrompt, userMessage, 800, 0.85);
    }
}
