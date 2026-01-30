package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 랜덤 게임 DTO
 */
@Data
public class Game {
    
    private int no;
    private int clubNo;
    private String gameType;
    private String title;
    private String options;
    private String result;
    private int createdBy;
    private Date createdAt;
    
    private User creator;   // 게임참여자 정보 (미니게임 소제목느낌 ex .. ㅁㅁㅁㅁ 의 미니게임 )
    private Club club;      // 모임 정보 (소속 클럽, 모임)
    
}