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
<<<<<<< HEAD


                    config.setAllowedOrigins(Arrays.asList("http://192.168.1.10:3000","http://192.168.219.125:3000", "http://localhost:3000", "http://192.168.1.36:3000", "http://192.168.1.11:3000", "http://192.168.1.19:3000" ,"http://172.30.1.29:3000"));

                    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "*"));
=======
                    config.setAllowedOrigins(Arrays.asList(
                            "http://192.168.1.10:3000",
                            "http://192.168.219.125:3000",
                            "http://localhost:3000",
                            "http://192.168.1.36:3000",
                            "http://192.168.1.11:3000",
                            "http://192.168.1.19:3000"
                    ));
                    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
>>>>>>> d435f6b305d258901ddc0f5c5acc824ac6a7b6cd
                    config.setAllowedMethods(Arrays.asList("*"));  // 모든 메서드를 명시적으로 허용
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 공격 방지 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
<<<<<<< HEAD
                		  // 인증 없이 접근 가능한 경로 설정
                        .requestMatchers(HttpMethod.GET, "/store/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/maps/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/photos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        // 나머지 모든 요청은 인증 필요
                                                                   
                        // 나머지 모든 요청은 인증 필요
=======
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/store/category/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/photos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/photos/store/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/mypage/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/members/userProfile/**").authenticated() // 인증된 사용자만 접근
>>>>>>> d435f6b305d258901ddc0f5c5acc824ac6a7b6cd
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
