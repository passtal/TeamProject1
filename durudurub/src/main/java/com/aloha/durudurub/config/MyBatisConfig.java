package com.aloha.durudurub.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 설정
 */
@Configuration
@MapperScan("com.aloha.durudurub.dao")
public class MyBatisConfig {
    // MyBatis 매퍼 스캔 설정
}