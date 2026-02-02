package com.aloha.durudurub.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aloha.durudurub.dto.Auth;
import com.aloha.durudurub.dto.User;

import lombok.Getter;

/**
 * Spring Security 사용자 정보
 */
@Getter
public class CustomUserDetails implements UserDetails {
    
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }
    

    /**
     * 권한 목록 반환
     * user_auth 테이블에서 가져온 ROLE_USER, ROLE_ADMIN 같은 값들을 반환
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Auth> authList = user.getAuthList();
        if (authList == null) return List.of();
        
        return authList.stream()
                .map(Auth::getAuth)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * DB에 저장된 암호화된 비밀번호 
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 로그인 ID
     * user_id(이메일) 로 로그인하므로 userId 리턴 
     */
    @Override
    public String getUsername() {
        return user.getUserId();
    }

    // 계정 만료 여부 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 나중에 기능 추가 한 후 
    //ex ) @Override
        // public boolean isEnabled() {
        // return !"Y".equals(user.getIsBanned());
        // }
    // = 차단된 유저 로그인 불가 
}