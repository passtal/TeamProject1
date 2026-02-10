package com.aloha.durudurub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드된 파일 서빙 (uploads 폴더)
        // String uploadPath = "file:" + System.getProperty("user.dir") + "/uploads/";
        // ******* 업로드 파일 서빙 (uploads? durudurub/uploads?)
        String uploadPath = "file:" + System.getProperty("user.dir") + "/durudurub/uploads/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
        
        // 프로필 이미지 서빙 (C:/durudurub_upload/profile/profileImg/)
        registry.addResourceHandler("/upload/profile/**")
                .addResourceLocations("file:C:/durudurub_upload/profile/profileImg/");


    }
}