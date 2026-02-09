package com.aloha.durudurub.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/banners/";
    private static final String DB_URL_PREFIX = "/uploads/banners/";

    // 리스트 조회
    @Override
    public List<Banner> bannerList() throws Exception {
        List<Banner> list = bannerMapper.bannerList();
        return list;
    }
    // 상세조회
    @Override
    public Banner bannerSelect(int no) throws Exception {
        Banner banner = bannerMapper.bannerSelect(no);
        return banner;
    }
    // 등록
    @Override
    @Transactional
    public int bannerInsert(Banner banner, MultipartFile imageFile) throws Exception {
        // isActive가 "N,Y" 같이 들어오면 마지막 값만 사용
        String isActive = banner.getIsActive();
        if (StringUtils.hasText(isActive) && isActive.contains(",")) {
            isActive = isActive.substring(isActive.lastIndexOf(",") + 1).trim();
        }
        if (!StringUtils.hasText(isActive)) isActive = "N"; // 기본값
        banner.setIsActive(isActive);

        // position 기본값
        if (!StringUtils.hasText(banner.getPosition())) {
            banner.setPosition("MAIN");
        }
        // 표시 순서 기본 '1'
        if (banner.getSeq() <= 0) banner.setSeq(1);
        // 파일 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            String savaUrl = saveBannerImage(imageFile);
            banner.setImageUrl(savaUrl);
        }
        // 파일 없고, URL 없으면 등록 불가!
        if (!StringUtils.hasText(banner.getImageUrl())) {
            throw new IllegalArgumentException("URL 또는 파일 업로드는 필수입니다");
        }
        return bannerMapper.bannerInsert(banner);
    }
    // 배너 수정
    @Override
    @Transactional
    public int bannerUpdate(Banner banner, MultipartFile imageFile) throws Exception {
        
        // 기존 배너 조회
        Banner old = bannerMapper.bannerSelect(banner.getNo());
        // 파일 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            String savaUrl = saveBannerImage(imageFile);
            banner.setImageUrl(savaUrl);
        } else {
            // 새파일 없으면 기존 유지
            if (!StringUtils.hasText(banner.getImageUrl()) && old != null) {
                banner.setImageUrl(old.getImageUrl());
            }
        }
        int result = bannerMapper.bannerUpdate(banner);

        // 기존 파일 삭제
        if (result > 0 && imageFile != null && !imageFile.isEmpty()) {
            if (StringUtils.hasText(old.getImageUrl()) && !old.getImageUrl().equals(banner.getImageUrl())) {
                deleteFileIfLocal(old.getImageUrl());
            }
        }

        return result;
    }
    // 배너 삭제
    @Override
    @Transactional
    public int bannerDelete(int no) throws Exception {
        // 기존 배너 조회 (삭제할 파일 경로 확보)
        Banner old = bannerMapper.bannerSelect(no);
        // DB 삭제
        int result = bannerMapper.bannerDelete(no);
        // DB 삭제 후 로컬 업로드 파일 삭제
        if (result > 0 && old != null) {
            deleteFileIfLocal(old.getImageUrl());
        }
        return result;
    }

    
    // 이미지 저장
    private String saveBannerImage(MultipartFile imageFile) throws IOException {
        // 디렉토리
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String originalName = imageFile.getOriginalFilename();
        String ext = "";

        if (originalName != null && originalName.contains(".")) {
            // .png
            ext = originalName.substring(originalName.lastIndexOf('.'));
        }

        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
        File dest = new File(dir, savedName);
        imageFile.transferTo(dest);


        System.out.println("**************SAVE PATH = " + dest.getAbsolutePath());
        System.out.println("**************URL = " + (DB_URL_PREFIX + savedName));

        // DB에 저장할 URL (정적 리소스 매핑 필요)
        return DB_URL_PREFIX + savedName;
    }
    private void deleteFileIfLocal(String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) return;
        // /uploads/... 경우 삭제
        if (!imageUrl.startsWith("/durudurub/uploads/")) return;
        // /uploads/banners/xxx.png 
        String relativePath = imageUrl.replaceFirst("^/durudurub/uploads/", "");
        File file = new File(System.getProperty("user.dir") + "/durudurub/uploads/" + relativePath);
        if (file.exists() && file.isFile()) {
            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println("파일 삭제 실패!" + file.getAbsolutePath());
            }
        }
    }

    // 배너 활성화 뱃지
    @Override
    public int updateBannerActive(int no, String isActive) throws Exception{
        return bannerMapper.updateBannerActive(no, isActive);
    }
    // 배너 위치 뱃지
    @Override
    public int updateBannerPosition(int no, String position) throws Exception{
        return bannerMapper.updateBannerPosition(no, position);
    }

    // 메인 배너
    @Override
    public List<Banner> getMainBanner() {
        return bannerMapper.selectMainBanner();
    }
}
