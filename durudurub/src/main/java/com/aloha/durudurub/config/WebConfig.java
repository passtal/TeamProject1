package com.aloha.durudurub.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드된 파일 서빙 (uploads 폴더)
        Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads").toAbsolutePath();
        String uploadLocation = uploadPath.toUri().toString();
        System.out.println("[WebConfig] uploadPath = " + uploadLocation);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadLocation);
        
        // 프로필 이미지 서빙 (C:/durudurub_upload/profile/profileImg/)
        registry.addResourceHandler("/upload/profile/**")
                .addResourceLocations("file:///C:/durudurub_upload/profile/profileImg/");
    }
}