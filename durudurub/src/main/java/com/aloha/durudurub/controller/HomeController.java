package com.aloha.durudurub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 메인 페이지 컨트롤러
 */


@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}