package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aloha.durudurub.dto.Auth;
import com.aloha.durudurub.dto.User;

/**
 * 회원 서비스
 */
public interface UserService {
    
    List<User> list();
    
    User selectByNo(int no);
    
    User selectByUserId(String userId);
    
    User selectByUsername(String username);
    
    int insert(User user);
    
    int update(User user);
    
    int delete(int no);
    
    int updatePassword(int no, String password);
    
    int updateProfileImg(int no, String profileImg);
    
    boolean existsByUserId(String userId);
    
    boolean existsByUsername(String username);
    
    List<Auth> selectAuthByUserNo(int userNo);
    
    int insertAuth(Auth auth);
    
    int deleteAuth(int userNo, String auth);

    int insert (User user, MultipartFile profileImgFile);
    

    // 관리자 페이지
    // 전체 사용자 수
    int countAll();
    // 최신 가입자 수
    int countNew();
}
