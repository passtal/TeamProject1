package com.aloha.durudurub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 랜덤 게임 컨트롤러
 */
@Controller
@RequestMapping("/game")
public class GameController {
    
    /**
     * 게임 목록 페이지
     */
    @GetMapping("/list")
    public String list() {
        return "game/list";
    }
    
    /**
     * 사다리 게임 페이지
     */
    @GetMapping("/ladder")
    public String ladder() {
        return "game/ladder";
    }
    
    /**
     * 룰렛 게임 페이지
     */
    @GetMapping("/roulette")
    public String roulette() {
        return "game/roulette";
    }
    
    /**
     * 랜덤 추첨 페이지
     */
    @GetMapping("/random")
    public String random() {
        return "game/random";
    }
}