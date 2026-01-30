package com.aloha.durudurub.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.durudurub.dto.Banner;
import com.aloha.durudurub.service.BannerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;





/**
 * 배너 관리 컨트롤러 (관리자 전용)
 */

@Slf4j
@Controller
@RequestMapping("/admin/banner")
@RequiredArgsConstructor
public class BannerController {
    // TODO: 구현
    private final BannerService bannerService;

    // 배너 목록
    @GetMapping("/")
    public String list(Model model) throws Exception {
        List<Banner> list = bannerService.list();
        model.addAttribute("list", list);
        return "/";
    }

    // 배너 조회
    @GetMapping("/")
    public String select(
        @RequestParam("no") Integer no
        ,Model model
    ) throws Exception {
        Banner banner = bannerService.select(no);
        model.addAttribute("banner", banner);
        return "/";
    }

    // 배너 등록 - 모달
    @GetMapping("/")
    public String create() {
        return "/";
    }
    
    @PostMapping("/")
    public String create(Banner banner) throws Exception {
        boolean result = bannerService.insert(banner);
        if (result) {
            return "redirect:/";
        }
        return "redirect:/?error";
    }

    // 배너 수정 - 모달
    @GetMapping("/")
    public String update(
        @RequestParam("no") Integer no,
        Model model
    ) throws Exception {
        Banner banner = bannerService.select(no);
        model.addAttribute("banner", banner);
        return "/";
    }
    
    @PostMapping("/")
    public String update(
        Banner banner
    ) throws Exception {
        boolean result = bannerService.update(banner);
        if (result) {
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
    // 배너 등록
}
