package com.aloha.durudurub.service;

import java.util.List;

import com.aloha.durudurub.dto.Banner;

/**
 * 배너 서비스
 */
public interface BannerService {
    // TODO: 구현

    // 배너 목록
    List<Banner> list() throws Exception;
    // 배너 조회
    Banner select(Integer no) throws Exception;
    // 배너 등록
    boolean insert(Banner banner) throws Exception;
    // 배너 수정
    boolean update(Banner banner) throws Exception;
    // 배너 삭제
    boolean delete(Integer no) throws Exception;
}
