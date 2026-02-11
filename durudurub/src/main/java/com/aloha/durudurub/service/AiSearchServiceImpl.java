package com.aloha.durudurub.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.durudurub.dao.ClubMapper;
import com.aloha.durudurub.dto.AiSearchResponse;
import com.aloha.durudurub.dto.Club;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AiSearchServiceImpl implements AiSearchService {

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private OpenAiService openAiService;

    @Override
    public AiSearchResponse search(String userMessage) throws Exception {

        // 1단계: AI에게 검색 키워드 + 동의어/유사어를 여러 개 추출
        String keywordsRaw = extractKeywords(userMessage);
        log.info("AI가 추출한 키워드들: {}", keywordsRaw);

        // 2단계: 키워드를 분리하여 각각 DB 검색, 중복 제거하며 합산
        String[] keywords = keywordsRaw.split("[,\\s]+");
        Set<Integer> foundClubNos = new HashSet<>();
        List<Club> clubs = new ArrayList<>();

        for (String kw : keywords) {
            String trimmed = kw.trim();
            if (trimmed.isEmpty()) continue;
            try {
                List<Club> result = clubMapper.search(trimmed);
                for (Club club : result) {
                    if (foundClubNos.add(club.getNo())) {
                        clubs.add(club);
                    }
                }
            } catch (Exception e) {
                log.warn("키워드 '{}' 검색 중 오류: {}", trimmed, e.getMessage());
            }
        }
        log.info("다중 키워드 검색 결과: {}건 (키워드 {}개)", clubs.size(), keywords.length);

        // 3단계: 검색 결과가 없으면 → 전체 모임을 AI에게 주고 추천
        List<Club> allClubs = null;
        if (clubs.isEmpty()) {
            log.info("키워드 검색 결과 없음 → 전체 모임 대상 AI 추천");
            allClubs = clubMapper.list();
        }

        // 4단계: AI 추천 메시지 생성
        String aiMessage = generateRecommendation(userMessage, clubs, allClubs);

        // 5단계: 검색 결과 없었지만 AI가 전체 모임에서 골라준 경우, 해당 모임 결과에 포함
        if (clubs.isEmpty() && allClubs != null && !allClubs.isEmpty()) {
            clubs = findMentionedClubs(aiMessage, allClubs);
        }

        // 대표 키워드
        String displayKeyword = keywords.length > 0 ? keywords[0].trim() : keywordsRaw;

        // 키워드 검색으로 직접 매칭된 결과가 있었는지 여부
        boolean isExactMatch = (allClubs == null);

        return AiSearchResponse.builder()
                .aiMessage(aiMessage)
                .clubs(clubs)
                .keyword(displayKeyword)
                .exactMatch(isExactMatch)
                .build();
    }

    /**
     * 키워드 추출 - 유사어/동의어까지 포함하여 여러 개 반환
     */
    private String extractKeywords(String userMessage) throws Exception {
        String systemPrompt =
            "너는 모임 플랫폼의 검색 키워드 추출 전문가야.\n" +
            "사용자의 메시지에서 모임을 검색하는 데 사용할 키워드들을 추출해줘.\n\n" +
            "## 규칙\n" +
            "1. 핵심 키워드 1~2개 + 그 동의어/유사어/관련어를 최대 5개까지 추출\n" +
            "2. 쉼표(,)로 구분해서 출력\n" +
            "3. 키워드만 출력해. 다른 설명 없이\n" +
            "4. 구어체/줄임말은 정식 명칭으로도 변환해서 포함해\n" +
            "5. 위치 정보가 있으면 포함해\n\n" +
            "## 예시\n" +
            "- '서울에서 등산 같이 할 사람?' → '등산, 산, 하이킹, 트레킹, 서울'\n" +
            "- '먹는거 좋아하는데' → '음식, 맛집, 요리, 쿠킹, 미식, 먹방'\n" +
            "- '나 mbti intp인데 추천해줘' → 'INTP, MBTI, 성격, 심리, 토론, 독서'\n" +
            "- '코딩 배우고 싶어' → '코딩, 프로그래밍, 개발, IT, 스터디'\n" +
            "- '카드놀이 좋아해' → '카드, 보드게임, 게임, 놀이, 카드게임'\n" +
            "- '운동 좀 하고 싶다' → '운동, 헬스, 피트니스, 스포츠, 러닝, 요가'";

        return openAiService.call(systemPrompt, userMessage, 100, 0.3);
    }

    /**
     * AI 추천 메시지 생성
     * - 검색 결과가 있으면 → 검색 결과 기반 추천
     * - 검색 결과가 없으면 → 전체 모임 데이터를 보고 가장 관련 있는 것 추천
     */
    private String generateRecommendation(String userMessage, List<Club> searchResults, List<Club> allClubs) throws Exception {
        StringBuilder clubInfo = new StringBuilder();

        if (!searchResults.isEmpty()) {
            // 검색 결과가 있는 경우
            for (Club club : searchResults) {
                String desc = club.getDescription() != null
                    ? club.getDescription().substring(0, Math.min(club.getDescription().length(), 60))
                    : "";
                clubInfo.append(String.format("- [%d] %s | %s | %s | %d/%d명 | %s\n",
                    club.getNo(), club.getTitle(),
                    club.getCategory() != null ? club.getCategory().getName() : "",
                    club.getLocation() != null ? club.getLocation() : "",
                    club.getCurrentMembers(), club.getMaxMembers(),
                    desc));
            }

            String systemPrompt =
                "너는 '두루두룹' 모임 플랫폼의 친근한 AI 추천 도우미 '두루'야.\n\n" +
                "## 규칙\n" +
                "1. 아래 검색 결과 중에서 사용자에게 가장 잘 맞는 모임을 추천해줘\n" +
                "2. 왜 이 모임이 어울리는지 이유를 친근하게 설명해줘\n" +
                "3. 이모지를 적절히 사용해서 친근하게 답변해줘\n" +
                "4. 3~5줄 정도로 답변해줘\n\n" +
                "[검색된 모임 목록]\n" + clubInfo.toString();

            return openAiService.call(systemPrompt, userMessage, 500, 0.8);

        } else if (allClubs != null && !allClubs.isEmpty()) {
            // 검색 결과가 없지만 전체 모임은 있는 경우 → AI가 직접 골라 추천
            int limit = Math.min(allClubs.size(), 40);
            for (int i = 0; i < limit; i++) {
                Club club = allClubs.get(i);
                String desc = club.getDescription() != null
                    ? club.getDescription().substring(0, Math.min(club.getDescription().length(), 60))
                    : "";
                clubInfo.append(String.format("- [%d] %s | %s | %s | %d/%d명 | %s\n",
                    club.getNo(), club.getTitle(),
                    club.getCategory() != null ? club.getCategory().getName() : "",
                    club.getLocation() != null ? club.getLocation() : "",
                    club.getCurrentMembers(), club.getMaxMembers(),
                    desc));
            }

            String systemPrompt =
                "너는 '두루두룹' 모임 플랫폼의 친근한 AI 추천 도우미 '두루'야.\n\n" +
                "## 상황\n" +
                "사용자의 질문에 정확히 매칭되는 모임은 없었어.\n" +
                "하지만 아래 전체 모임 목록 중에서 사용자의 관심사와 가장 가까운 모임을 찾아서 추천해줘.\n\n" +
                "## 규칙\n" +
                "1. 사용자의 관심사를 파악하고, 그에 가까운 모임 1~3개를 골라서 추천해\n" +
                "2. 정확히 일치하지 않더라도 관련될 수 있는 모임을 창의적으로 연결해서 추천해\n" +
                "3. '정확히 원하시는 모임은 아직 없지만, 이런 모임은 어떠세요?' 같은 톤으로\n" +
                "4. 추천할 모임의 제목을 정확히 언급해줘 (목록에서 골라)\n" +
                "5. 정말 관련 모임이 없으면 직접 모임을 만들어보라고 안내해줘\n" +
                "6. 이모지를 적절히 사용해서 친근하게 4~6줄로 답변해줘\n\n" +
                "[현재 운영 중인 전체 모임 목록]\n" + clubInfo.toString();

            return openAiService.call(systemPrompt, userMessage, 600, 0.85);

        } else {
            return "😅 아직 등록된 모임이 없어요! 관심 있는 모임을 직접 만들어보시는 건 어떨까요?";
        }
    }

    /**
     * AI 추천 메시지에서 언급된 모임을 전체 목록에서 찾아 반환
     */
    private List<Club> findMentionedClubs(String aiMessage, List<Club> allClubs) {
        List<Club> mentioned = new ArrayList<>();
        if (aiMessage == null || allClubs == null) return mentioned;

        for (Club club : allClubs) {
            if (club.getTitle() != null && aiMessage.contains(club.getTitle())) {
                mentioned.add(club);
                if (mentioned.size() >= 5) break;
            }
        }
        return mentioned;
    }
}