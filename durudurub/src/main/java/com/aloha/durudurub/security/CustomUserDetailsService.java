package com.aloha.durudurub.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aloha.durudurub.dao.AuthMapper;
import com.aloha.durudurub.dao.UserMapper;
import com.aloha.durudurub.dto.Auth;
import com.aloha.durudurub.dto.User;

/**
 * Spring Security 사용자 상세 서비스
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 회원 조회 
        User user = userMapper.selectByUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다");
        }

        // 권한 조회
        List<Auth> authList = authMapper.selectByUserNo(user.getNo());

        // user에 권한 세팅
        user.setAuthList(authList);

        return new CustomUserDetails(user);

    }
}
