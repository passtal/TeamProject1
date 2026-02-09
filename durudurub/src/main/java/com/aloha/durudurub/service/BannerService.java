package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aloha.durudurub.dto.Banner;

/**
 * 배너 서비스
 */
public interface BannerService {

    // 배너 목록
    List<Banner> bannerList() throws Exception;
    // 배너 조회
    Banner bannerSelect(int no) throws Exception;
    // 배너 등록
    int bannerInsert(Banner banner, MultipartFile imageFile) throws Exception;
    // 배너 수정
    int bannerUpdate(Banner banner, MultipartFile imageFile) throws Exception;
    // 배너 삭제
    int bannerDelete(int no) throws Exception;
    // 배너 활성화 뱃지
    int updateBannerActive(int no, String isActive) throws Exception;
    // 배너 위치 뱃지
    int updateBannerPosition(int no, String position) throws Exception;
    // 메인 배너
    List<Banner> getMainBanner();
}
