package com.aloha.durudurub.service;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.AuthMapper;
import com.aloha.durudurub.dao.UserMapper;
import com.aloha.durudurub.dto.Auth;
import com.aloha.durudurub.dto.User;

import lombok.RequiredArgsConstructor;

/**
 * 회원 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    
    private final AuthMapper authMapper;
    
    private final PasswordEncoder passwordEncoder;
    
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
    @Transactional
    public int insert(User user, org.springframework.web.multipart.MultipartFile profileImgFile) {
        // 프로필 이미지 처리 (필요시 파일 저장 로직 추가)
        if (profileImgFile != null && !profileImgFile.isEmpty()) {
            // TODO: 파일 저장 로직 구현
            // 임시로 파일명만 저장
            user.setProfileImg(profileImgFile.getOriginalFilename());
        }
        
        // 기존 insert 메서드 호출
        return insert(user);
    }
}
