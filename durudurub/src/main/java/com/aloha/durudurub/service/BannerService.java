package com.aloha.durudurub.service;

import java.util.List;

import com.aloha.durudurub.dto.Banner;

/**
 * 배너 서비스
 */
public interface BannerService {

    // 배너 목록
    List<Banner> bannerList() throws Exception;
    // 배너 조회
    Banner bannerSelect(Integer no) throws Exception;
    // 배너 등록
    boolean bannerInsert(Banner banner) throws Exception;
    // 배너 수정
    boolean bannerUpdate(Banner banner) throws Exception;
    // 배너 삭제
    boolean bannerDelete(Integer no) throws Exception;
}
