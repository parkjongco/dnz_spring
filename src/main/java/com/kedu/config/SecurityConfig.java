package com.kedu.config;

import com.kedu.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//10:01 push
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();

                    config.setAllowedOrigins(Arrays.asList("http://192.168.1.10:3000", "http://localhost:3000", "http://192.168.1.11:3000", "http://192.168.1.19:3000"));
                    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "*"));
                    config.setAllowedMethods(Arrays.asList("*"));  // 모든 메서드를 명시적으로 허용
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 공격 방지 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        // "/auth/**"는 인증 없이 접근 가능
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()

                        // "/store/category/**"는 인증 없이 접근 가능
                        .requestMatchers(HttpMethod.GET, "/store/category/**").permitAll()

                        // "/photos/**" 경로는 인증 없이 접근 가능
                        .requestMatchers(HttpMethod.GET, "/photos/**").permitAll()

                        // 나머지 모든 요청은 인증 필요
                        .anyRequest().authenticated())
                // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 패스워드 암호화
    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
