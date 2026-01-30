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
    public List<Banner> list() throws Exception {
        List<Banner> list = bannerMapper.list();
        return list;
    }

    @Override
    public Banner select(Integer no) throws Exception {
        Banner banner = bannerMapper.select(no);
        return banner;
    }

    @Override
    public boolean insert(Banner banner) throws Exception {
        int result = bannerMapper.insert(banner);
        return result > 0;
    }

    @Override
    public boolean update(Banner banner) throws Exception {
        int result = bannerMapper.update(banner);
        return result > 0;
    }
    
    @Override
    public boolean delete(Integer no) throws Exception {
        int result = bannerMapper.delete(no);
        return result > 0;
    }

}
