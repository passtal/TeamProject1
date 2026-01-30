package com.aloha.design.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        // 카테고리 데이터
        model.addAttribute("categories", new String[]{
            "자기계발", "스포츠", "푸드", "게임", "동네친구", "여행", "예술", "반려동물"
        });

        // 광고 배너 데이터
        model.addAttribute("adTitle", "Coffee House");
        model.addAttribute("adDesc", "프리미엄 원두 커피");
        model.addAttribute("adDiscount", "신규 회원 20% 할인");

        return "index";
    }
}
