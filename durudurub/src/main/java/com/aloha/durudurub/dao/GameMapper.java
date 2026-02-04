package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.durudurub.dto.Game;

/**
 * 랜덤 게임 매퍼
 */
@Mapper
public interface GameMapper {
    
    /**
     * 모임별 게임 목록 조회
     */
    List<Game> listByClub(int clubNo);
    
    /**
     * 게임 상세 조회
     */
    Game select(int no);
    
    /**
     * 게임 생성
     */
    int insert(Game game);
    
    /**
     * 게임 결과 업데이트
     */
    int updateResult(Game game);
    
    /**
     * 게임 삭제
     */
    int delete(int no);
}
