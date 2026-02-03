package com.aloha.durudurub.service;

import java.util.List;

import com.aloha.durudurub.dto.Game;

/**
 * 랜덤게임 서비스
 */
public interface GameService {
    
    /**
     * 모임별 게임 목록 조회
     */
    List<Game> getGamesByClub(int clubNo);
    
    /**
     * 게임 상세 조회
     */
    Game getGameByNo(int no);
    
    /**
     * 게임 생성
     */
    int createGame(Game game);
    
    /**
     * 게임 결과 업데이트
     */
    int updateGameResult(Game game);
    
    /**
     * 게임 삭제
     */
    int deleteGame(int no);
}