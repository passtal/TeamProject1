package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.User;

/**
 * 회원 매퍼
 */
@Mapper
public interface UserMapper {
    
    List<User> list();
    
    User selectByNo(@Param("no") int no);
    
    User selectByUserId(@Param("userId") String userId);
    
    User selectByUsername(@Param("username") String username);
    
    int insert(User user);
    
    int update(User user);
    
    int delete(@Param("no") int no);
    
    int updatePassword(@Param("no") int no, @Param("password") String password);
    
    int updateProfileImg(@Param("no") int no, @Param("profileImg") String profileImg);
    
    int countByUserId(@Param("userId") String userId);
    
    int countByUsername(@Param("username") String username);

    int countAll();
    
}
