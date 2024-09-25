package com.kedu.filters;

import com.kedu.services.MembersService;
import com.kedu.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    @Lazy
    private MembersService membersService;
    @Autowired
    private JwtUtil jwt;

 // Authorization 헤더에서 JWT 토큰 추출
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization 헤더: " + authHeader);
            return authHeader.substring(7);  // "Bearer " 이후의 토큰 부분만 반환
        } else {         
            // 쿼리 파라미터에서 토큰을 추출
            String token = request.getParameter("token");
            if (token != null) {
                System.out.println("쿼리 파라미터에서 추출된 토큰: " + token);
                return token;
            }
        }
        return null;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // JWT 토큰 추출
//        String token = extractToken(request);
//
//        if (token != null && !token.isEmpty()) {
//            System.out.println("JWT 필터에서 추출된 토큰: " + token);
//
//            // 토큰이 유효한지 검증
//            if (jwt.isVerfied(token) ) {
//                String userId = jwt.getSubject(token);  // 토큰에서 사용자 ID 추출
//                UserDetails userDetails = membersService.loadUserByUsername(userId);
//
//                // 유효한 사용자라면 SecurityContext에 설정
//                if (userDetails != null) {
//                    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(auth);
//                }
//            } else {
//                System.out.println("토큰이 유효하지 않음.");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//        } else {
//            System.out.println("JWT 필터에서 추출된 토큰: null");
//        }
//
//        // 필터 체인 계속 실행
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // JWT 토큰 추출
        String token = extractToken(request);

        if (token != null && !token.isEmpty()) {
            System.out.println("JWT 필터에서 추출된 토큰: " + token);

            try {
                // 토큰이 유효한지 검증
                if (jwt.isVerfied(token)) {
                    String userId = jwt.getSubject(token);  // 토큰에서 사용자 ID 추출
                    UserDetails userDetails = membersService.loadUserByUsername(userId);

                    // 유효한 사용자라면 SecurityContext에 설정
                    if (userDetails != null) {
                        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        System.out.println("유효한 사용자 정보를 찾을 수 없음.");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                } else {
                    System.out.println("토큰이 유효하지 않음.");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } catch (Exception e) {
                System.out.println("JWT 검증 중 오류 발생: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            System.out.println("JWT 필터에서 추출된 토큰: null");
        }

        // 필터 체인 계속 실행
        filterChain.doFilter(request, response);
    }


}
