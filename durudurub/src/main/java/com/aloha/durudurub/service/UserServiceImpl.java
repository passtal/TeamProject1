package com.aloha.durudurub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.AuthMapper;
import com.aloha.durudurub.dao.UserMapper;
import com.aloha.durudurub.dto.User;

import lombok.RequiredArgsConstructor;

/**
 * 회원 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private PasswordEncoder passwordEncoder; // @Bean 등록 필수! 

    /**
     * 이메일(user_id) 중복 여부 확인
     * @return true(중복), false(사용 가능)
     */
    @Override
    public boolean existsUserId(String userId) {
        return userMapper.selectByUserId(userId) != null;
    }
    
    /**
     * 닉네임(username) 중복 여부 확인 
     * @return true(중복), false(사용 가능)
     */
    @Override
    public boolean existsUsername(String username) {
        return userMapper.selectByUserName(username) != null;
    }

    /**
     * 회원가입 
     */
    @Override
    @Transactional
    public int signup(User user) {

        if (user == null) throw new IllegalArgumentException("회원 정보가 비어있습니다.");
        if (user.getUserId() == null || user.getUserId().isBlank()) throw new IllegalArgumentException("이메일은 필수입니다.");
        if (user.getUsername() == null || user.getUsername().isBlank()) throw new IllegalArgumentException("닉네임은 필수입니다.");
        if (user.getPassword() == null || user.getPassword().isBlank()) throw new IllegalArgumentException("비밀번호는 필수입니다.");
        
        // 최종 중복 체크 
        if (existsUserId(user.getUserId())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        if (existsUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }        

        //  비밀번호 BCrypt 암호화
        String encodePw = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePw);

        // 회원 저장 (PK no)
        int result = userMapper.insert(user);
        if (result <= 0) {
            throw new IllegalStateException("회원가입 실패");
        }

        // 기본 권한 부여 (ROLE_USER)
        authMapper.insert(user.getNo(), "ROLE_USER");

        return result;

        
    }

}
