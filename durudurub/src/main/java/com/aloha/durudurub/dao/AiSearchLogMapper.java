package com.aloha.durudurub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.AiSearchLog;

@Mapper
public interface AiSearchLogMapper {

    // 검색 로그 저장
    int insert(AiSearchLog log);

    // 총 검색 횟수 조회
    int countByUserNo(@Param("userNo") int userNo);
}
