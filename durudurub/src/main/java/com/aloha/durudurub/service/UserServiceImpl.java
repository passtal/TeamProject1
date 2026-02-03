package com.aloha.durudurub.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.durudurub.dao.AuthMapper;
import com.aloha.durudurub.dao.UserMapper;
import com.aloha.durudurub.dto.Auth;
import com.aloha.durudurub.dto.User;

import lombok.RequiredArgsConstructor;

/**
 * 회원 서비스 구현체
 */
@Service
@RequiredArgsConstructor // 임시
public class UserServiceImpl implements UserService {
    
    // @Autowired
    private UserMapper userMapper;
    // @Autowired
    private AuthMapper authMapper;
    // @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public List<User> list() {
        return userMapper.list();
    }
    
    @Override
    public User selectByNo(int no) {
        return userMapper.selectByNo(no);
    }
    
    @Override
    public User selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }
    
    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    @Override
    @Transactional
    public int insert(User user) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        int result = userMapper.insert(user);
        
        // 기본 권한 부여
        if (result > 0) {
            Auth auth = new Auth();
            auth.setUserNo(user.getNo());
            auth.setAuth("ROLE_USER");
            authMapper.insert(auth);
        }
        
        return result;
    }
    
    @Override
    public int update(User user) {
        return userMapper.update(user);
    }
    
    @Override
    @Transactional
    public int delete(int no) {
        // 권한 먼저 삭제 (외래키제약조건때문에...)
        authMapper.deleteByUserNo(no);
        return userMapper.delete(no);
    }
    
    @Override
    public int updatePassword(int no, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userMapper.updatePassword(no, encodedPassword);
    }
    
    @Override
    public int updateProfileImg(int no, String profileImg) {
        return userMapper.updateProfileImg(no, profileImg);
    }
    
    @Override
    public boolean existsByUserId(String userId) {
        return userMapper.countByUserId(userId) > 0;
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userMapper.countByUsername(username) > 0;
    }
    
    @Override
    public List<Auth> selectAuthByUserNo(int userNo) {
        return authMapper.listByUserNo(userNo);
    }
    
    @Override
    public int insertAuth(Auth auth) {
        return authMapper.insert(auth);
    }
    
    @Override
    public int deleteAuth(int userNo, String auth) {
        return authMapper.delete(userNo, auth);
    }

    @Override
    public int insert(User user, MultipartFile profileImgFile) {
        // ✅ 프로필 이미지 저장(선택)
    if (profileImgFile != null && !profileImgFile.isEmpty()) {

        // 1) 간단 검증: 이미지 파일인지 + 용량 제한(5MB)
        String contentType = profileImgFile.getContentType();
        long size = profileImgFile.getSize();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("프로필 사진은 이미지 파일만 업로드할 수 있습니다.");
        }
        long maxBytes = 5L * 1024 * 1024; // 5MB
        if (size > maxBytes) {
            throw new IllegalArgumentException("프로필 사진은 5MB 이하만 가능합니다.");
        }

        // 2) 저장 폴더 준비
        String uploadDir = "C:/durudurub_upload/profile/profileImg";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // 3) 확장자 추출(원본 파일명 기반)
        String original = profileImgFile.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf(".")); // ".jpg"
        }

        // 4) 저장 파일명(UUID)
        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

        // 5) 실제 저장
        File savedFile = new File(dir, savedName);
        try {
            profileImgFile.transferTo(savedFile);
        } catch (Exception e) {
            throw new RuntimeException("프로필 이미지 저장에 실패했습니다.");
        }

        // 6) DB에 저장할 '접근 URL' 세팅 (WebConfig에서 /upload/profile/** 매핑할 예정)
        String publicUrl = "/upload/profile/" + savedName;
        user.setProfileImg(publicUrl);
    }

        // ✅ 기존 회원가입 로직 재사용
        return insert(user);
    }
}
