package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Auth;

/**
 * 회원 권한 매퍼
 */
@Mapper
public interface AuthMapper{

    // 특정 회원 권한 목록 조회 
    public List<Auth> selectByUserNo(int userNo);

    // 회원 가입 시 권한 부여
    public int insert(@Param("userNo") int userNo, @Param("auth") String auth );

    



}
