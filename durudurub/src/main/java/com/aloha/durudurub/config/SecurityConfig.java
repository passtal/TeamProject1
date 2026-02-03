package com.aloha.durudurub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // TODO: 구현

    // 스프링 시큐리티 설정 메서드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         // ✅ 인가 설정
        // requestMatchers : 어디까지 허용해줄 것인가?
        // 인가 : 큰 틀 / 메서드 : 세부 틀
        http.authorizeHttpRequests(auth -> auth
                                    .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/**").permitAll());   // 전체 허용
        // 기본 로그인 처리 와 로그인 ? 처리한대 (람다식)
        // 🔐 폼 로그인 설정
        // 폼 로그인 : 폼 태그에서 로그인하는 기능? -> 기본 로그인 화면 제공! (html 없어도 제공!)
        // /login /logout : post 방식으로 기본 제공
        // login -> username, password 디폴트!
        // login 성공 시 기본 값은 이전 경로!(홈 화면이 아닌!)
        // -> 무조건 홈 화면으로 가려면 따로 설정 필요!
        http.formLogin( login -> login.loginPage("/login") // 커스텀 로그인 페이지 경로
                                .loginProcessingUrl("/login")// 로그인 요청 경로
                                // .usernameParameter("id")    // 아이디 파라미터명
                                // // .passwordParameter("pw"));  // 비밀번호 파라미터명
                                // .defaultSuccessUrl("/?login=true")
                                // .failureUrl("/login?error=true"));    // 로그인 성공 시 이동할 경로
        );
        // 인가 설정되어 있어야 폼 로그인 실행 가능!

        
        return http.build();
    }
    /**
     * 암호화 방식 빈 등록
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}