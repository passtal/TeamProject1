package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Banner;

/**
 * 배너 매퍼
 */
@Mapper
public interface BannerMapper {

    // 배너 목록
    List<Banner> bannerList() throws Exception;
    // 배너 조회
    Banner bannerSelect(@Param("no") int no) throws Exception;
    // 배너 등록
    int bannerInsert(Banner banner) throws Exception;
    // 배너 수정
    int bannerUpdate(Banner banner) throws Exception;
    // 배너 삭제
    int bannerDelete(@Param("no") int no) throws Exception;
    // 배너 활성화 뱃지
    int updateBannerActive(@Param("no") int no, @Param("isActive") String isActive);
    // 배너 위치 뱃지
    int updateBannerPosition(@Param("no") int no, @Param("position") String position);
    // 메인 배너
    List<Banner> selectMainBanner();
    
}
