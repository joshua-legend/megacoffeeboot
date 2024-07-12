package com.example.megacoffee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
            // HTTP 기본 인증을 사용하지 않습니다. (옵션: 필요에 따라 비활성화 가능)
            .httpBasic(Customizer.withDefaults())
            // CSRF 보호를 비활성화합니다. REST API의 경우 일반적으로 비활성화합니다.
            .csrf(AbstractHttpConfigurer::disable);
            // SecurityFilterChain을 빌드하여 반환합니다.
        return http.build();
    }
}
