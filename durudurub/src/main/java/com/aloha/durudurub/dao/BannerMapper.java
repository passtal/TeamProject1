package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.durudurub.dto.Banner;

/**
 * 배너 매퍼
 */
@Mapper
public interface BannerMapper {

    // 배너 목록
    List<Banner> bannerList() throws Exception;
    // 배너 조회
    Banner bannerSelect(Integer no) throws Exception;
    // 배너 등록
    int bannerInsert(Banner banner) throws Exception;
    // 배너 수정
    int bannerUpdate(Banner banner) throws Exception;
    // 배너 삭제
    int bannerDelete(Integer no) throws Exception;
    
}