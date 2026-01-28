package com.aloha.durudurub.dao;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.durudurub.dto.User;

/**
 * 회원 매퍼
 */
@Mapper
public interface UserMapper {

    // Id(Email) 회원 조회
    public User selectByUserId(String userId);

    // 닉네임으로 회원 조회
    public User selectByUserName(String userName);

    // 회원 가입
    public int insert(User user);

    // 회원 수정
    public int update(User user);

    // 회원 번호로 회원 조회
    public User selectByNo(int no);


}
