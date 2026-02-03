package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.durudurub.dto.Banner;

/**
 * 신고 매퍼
 */
@Mapper
public interface ReportMapper {
    // TODO: 구현
    // 임시 코드

   // 배너 목록
    List<Banner> bannerList() throws Exception;
}