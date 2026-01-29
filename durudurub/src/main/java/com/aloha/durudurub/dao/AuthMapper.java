package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Auth;

/**
 * 회원 권한 매퍼
 */
@Mapper
public interface AuthMapper {
    
    List<Auth> listByUserNo(@Param("userNo") int userNo);
    
    int insert(Auth auth);
    
    int delete(@Param("userNo") int userNo, @Param("auth") String auth);
    
    int deleteByUserNo(@Param("userNo") int userNo);

}