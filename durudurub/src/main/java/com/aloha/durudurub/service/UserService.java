package com.aloha.durudurub.service;

import com.aloha.durudurub.dto.User;

/**
 * 회원 서비스
 */
public interface UserService {

    // 회원가입 
    int signup(User user);

    // 이메일 중복 여부
    boolean existsUserId(String userId);

    // 닉네임 중복 여부 
    boolean existsUsername(String username);

}
