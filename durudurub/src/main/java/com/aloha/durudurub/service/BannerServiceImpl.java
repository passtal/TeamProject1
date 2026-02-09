package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aloha.durudurub.dao.BannerMapper;
import com.aloha.durudurub.dto.Banner;

import lombok.RequiredArgsConstructor;

/**
 * 배너 서비스 구현체
 */

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    
    private final BannerMapper bannerMapper;

    @Override
    public List<Banner> bannerList() throws Exception {
        List<Banner> list = bannerMapper.bannerList();
        return list;
    }

    @Override
    public Banner bannerSelect(Integer no) throws Exception {
        Banner banner = bannerMapper.bannerSelect(no);
        return banner;
    }

    @Override
    public boolean bannerInsert(Banner banner) throws Exception {
        int result = bannerMapper.bannerInsert(banner);
        return result > 0;
    }

    @Override
    public boolean bannerUpdate(Banner banner) throws Exception {
        int result = bannerMapper.bannerUpdate(banner);
        return result > 0;
    }
    
    @Override
    public boolean bannerDelete(Integer no) throws Exception {
        int result = bannerMapper.bannerDelete(no);
        return result > 0;
    }

    @Override
    public List<Banner> getMainBanner() {
        return bannerMapper.selectMainBanner();
    }

}
