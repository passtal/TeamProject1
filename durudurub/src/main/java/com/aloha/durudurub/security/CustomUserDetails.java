package com.aloha.durudurub.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;

/**
 * Spring Security 사용자 정보
 */
@Data
public class CustomUserDetails implements UserDetails {
    // TODO: 구현
}
