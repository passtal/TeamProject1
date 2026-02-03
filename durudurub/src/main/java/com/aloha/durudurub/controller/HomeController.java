package com.aloha.durudurub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aloha.durudurub.dto.Category;
import com.aloha.durudurub.service.CategoryService;

/**
 * 메인 페이지 컨트롤러
 */
@Controller
public class HomeController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/")
    public String index(Model model) {
        try {
            List<Category> categories = categoryService.list();
            if (categories == null) {
                categories = new ArrayList<>();
            }
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("categories", new ArrayList<>());
        }
        return "index copy";
    }
}