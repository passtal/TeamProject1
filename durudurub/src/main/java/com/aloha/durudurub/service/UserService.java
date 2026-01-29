package com.aloha.durudurub.service;

import com.aloha.durudurub.dto.User;

public interface UserService {

    // 로그인 사용자 조회 (principal.getName() = userId(email))
    User selectByUserId(String userId);

    // 회원번호로 조회
    User selectByNo(int no);

    // 이메일/닉네임 중복 체크
    boolean existsUserId(String userId);
    boolean existsUsername(String username);

    // 회원가입 (성공 시 가입된 회원 PK 반환을 추천)
    int signup(User user);

    // (선택) 회원정보 수정
    int update(User user);
}
