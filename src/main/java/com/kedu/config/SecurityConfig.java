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

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Arrays.asList(
                            "http://192.168.1.10:3000",
                            "http://192.168.219.182:3000",
                            "http://localhost:3000",
                            "http://172.30.1.97:3000",
                            "http://192.168.1.11:3000",
                            "http://192.168.1.19:3000",
                            "http://172.30.1.29:3000",
                            "http://192.168.1.172:3000"
                    ));
                    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "*"));
                    config.setAllowedMethods(Arrays.asList("*"));  // 모든 메서드를 명시적으로 허용
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 공격 방지 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        // 인증 없이 접근 가능한 경로 설정
                        .requestMatchers(HttpMethod.GET, "/store/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/maps/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/photos/store/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/menu/store/**").permitAll() // 가게 ID로 메뉴 조회 허용
                        .requestMatchers(HttpMethod.GET, "/menu/**").permitAll() // 메뉴 ID로 메뉴 조회 허용
                        .requestMatchers(HttpMethod.POST, "/menu/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/menu/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/menu/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/replies/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/replies/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/reviews/**").permitAll() // 리뷰 작성 허용
                        .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll() // 리뷰 조회 허용
                        .requestMatchers(HttpMethod.PUT, "/reviews/**").permitAll() // 리뷰 수정 허용
                        .requestMatchers(HttpMethod.DELETE, "/reviews/**").permitAll() // 리뷰 삭제 허용


                        .requestMatchers("/reservation/**").permitAll() // 모든 사용자 접근 허용

                        .requestMatchers("/api/activities/**").permitAll()
                        .requestMatchers("/members/**").permitAll() // 모든 사용자에게 접근 허용
                        .requestMatchers("/bookmark/**").hasAnyRole("ADMIN", "OWNER", "USER")

                        .requestMatchers(HttpMethod.POST, "/api/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/registerOwner").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/registerUser").permitAll()  // 모든 사용자에게 접근 허용

                        // 역할에 따른 접근 제어 추가
                        .requestMatchers("/admin/**").hasRole("ADMIN")   // 관리자만 접근 가능
                        .requestMatchers("/owner/**").hasAnyRole("ADMIN", "OWNER")   // 관리자 또는 점주 접근 가능
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "OWNER", "USER")     // 관리자, 점주, 일반 사용자 접근 가능


                        .anyRequest().authenticated()
                )
                // JWT 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
