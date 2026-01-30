package com.aloha.durudurub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.AuthMapper;
import com.aloha.durudurub.dao.UserMapper;
import com.aloha.durudurub.dto.User;

/**
 * 회원 서비스 구현체
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public User selectByNo(int no) {
        return userMapper.selectByNo(no);
    }

    @Override
    public boolean existsUserId(String userId) {
        return userMapper.selectByUserId(userId) != null;
    }

    @Override
    public boolean existsUsername(String username) {
        return userMapper.selectByUsername(username) != null;
    }

    /**
     * 회원가입
     * @return 가입 성공 시 생성된 user.no(PK) 반환
     */
    @Override
    @Transactional
    public int signup(User user) {

        // 최소 검증
        if (user == null) throw new IllegalArgumentException("회원 정보가 비어있습니다.");
        if (user.getUserId() == null || user.getUserId().isBlank()) throw new IllegalArgumentException("이메일은 필수입니다.");
        if (user.getUsername() == null || user.getUsername().isBlank()) throw new IllegalArgumentException("닉네임은 필수입니다.");
        if (user.getPassword() == null || user.getPassword().isBlank()) throw new IllegalArgumentException("비밀번호는 필수입니다.");

        // 최종 중복 체크
        if (existsUserId(user.getUserId())) throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        if (existsUsername(user.getUsername())) throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");

        // 비밀번호 BCrypt 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // users 저장 (useGeneratedKeys로 user.no 채워짐)
        int insertedRows = userMapper.insert(user);
        if (insertedRows <= 0) throw new IllegalStateException("회원가입 실패");

        // user_auth 저장 (기본 권한)
        int authRows = authMapper.insert(user.getNo(), "ROLE_USER");
        if (authRows <= 0) throw new IllegalStateException("권한 등록 실패");

        // 의미 있는 값 반환: 가입된 회원 PK
        return user.getNo();
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }
}
