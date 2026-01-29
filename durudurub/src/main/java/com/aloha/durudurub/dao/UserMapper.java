package com.aloha.durudurub.dao;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.durudurub.dto.User;

/**
 * 회원 매퍼
 */
@Mapper
public interface UserMapper {

    // Id(Email) 회원 조회
    User selectByUserId(String userId);

    // 닉네임(username) 회원 조회
    User selectByUsername(String username);

    // 회원 가입
    int insert(User user);

    // 회원 수정
    int update(User user);

    // 회원 번호로 회원 조회
    User selectByNo(int no);
}
