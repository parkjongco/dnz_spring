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
                    config.setAllowedMethods(Arrays.asList("*"));  // 허용되는 HTTP 메서드 설정
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 공격 방지 비활성화
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
                                .requestMatchers(HttpMethod.GET, "/menu/store/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/menu/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/menu/**").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.PUT, "/menu/**").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.DELETE, "/menu/**").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.POST, "/replies/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/replies/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/store/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/store/register").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.PUT, "/store/update").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.GET, "/store/info").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.GET, "/store/{storeSeq}/name").permitAll()
                                .requestMatchers(HttpMethod.GET, "/store/category/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/store/{storeId}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/store/all").permitAll()
                                .requestMatchers(HttpMethod.POST, "/store/register").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.GET, "/store/info").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.PUT, "/store/update").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.GET, "/store/{storeId}/photos").permitAll()
                                .requestMatchers(HttpMethod.GET, "/store/{storeSeq}/photos").permitAll()
                                .requestMatchers(HttpMethod.POST, "/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/reviews/**").permitAll()
                                .requestMatchers("/reservation/**").permitAll()
                                .requestMatchers("/api/activities/**").permitAll()
                                .requestMatchers("/members/**").permitAll()
                                .requestMatchers("/bookmark/**").hasAnyRole("ADMIN", "OWNER", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/posts/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/registerOwner").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.POST, "/registerUser").permitAll()
// 공지사항 관련 접근 제어 추가
                                .requestMatchers(HttpMethod.GET, "/store/{storeSeq}/notice").permitAll() // 특정 가게 공지사항 조회
                                .requestMatchers(HttpMethod.GET, "/notice").hasAnyRole("ADMIN", "OWNER") // 가게 공지사항 조회
                                .requestMatchers(HttpMethod.POST, "/notice").hasAnyRole("ADMIN", "OWNER") // 가게 공지사항 추가
                                .requestMatchers(HttpMethod.PUT, "/notice").hasAnyRole("ADMIN", "OWNER") // 가게 공지사항 수정
// 역할에 따른 접근 제어
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/owner/**").hasAnyRole("ADMIN", "OWNER")
                                .requestMatchers("/user/**").hasAnyRole("ADMIN", "OWNER", "USER")
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
