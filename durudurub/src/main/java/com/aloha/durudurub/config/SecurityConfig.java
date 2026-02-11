package com.aloha.durudurub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 스프링 시큐리티 설정 메서드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 (API 요청을 위해 일부 경로 제외)
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**", "/payments/**", "/confirm/**")
        );


        // ✅ 인가 설정
        http.authorizeHttpRequests(auth -> auth
                                    .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/club/create", "/club/*/edit", "/club/*/delete").authenticated()
                                    .requestMatchers("/club/*/board/**").authenticated()
                                    .requestMatchers("/users/mypage/**").authenticated()
                                    .requestMatchers("/**").permitAll());

        // 🔐 폼 로그인 설정
        http.formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
        );

        // 🚪 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        return http.build();
    }
    
    /**
     * 암호화 방식 빈 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 정적 리소스(업로드 파일 등)를 시큐리티 필터에서 제외
     */
    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    //     return (web) -> web.ignoring()
    //             .requestMatchers("/uploads/**", "/upload/**", "/css/**", "/js/**", "/img/**", "/fonts/**");
    // }
}
