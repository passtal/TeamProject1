package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.durudurub.dao.GameMapper;
import com.aloha.durudurub.dto.Game;

/**
 * 랜덤 게임 서비스 구현체
 */
@Service
public class GameServiceImpl implements GameService {
    
    @Autowired
    private GameMapper gameMapper;
    
    @Override
    public List<Game> getGamesByClub(int clubNo) {
        return gameMapper.listByClub(clubNo);
    }
    
    @Override
    public Game getGameByNo(int no) {
        return gameMapper.select(no);
    }
    
    @Override
    public int createGame(Game game) {
        return gameMapper.insert(game);
    }
    
    @Override
    public int updateGameResult(Game game) {
        return gameMapper.updateResult(game);
    }
    
    @Override
    public int deleteGame(int no) {
        return gameMapper.delete(no);
    }
}
