package com.kedu.interceptors;

import com.kedu.utils.JwtUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("WebSocket 연결 시도");

        // URI 쿼리 파라미터에서 JWT 토큰 추출
        String token = extractTokenFromRequest(request);

        // 토큰 검증
        if (token != null && jwtUtil.isVerfied(token)) {
            String userId = jwtUtil.getSubject(token);  // 토큰에서 사용자 ID 추출
            attributes.put("user", userId);  // WebSocket 세션에 사용자 정보 추가
            System.out.println("WebSocket 연결 성공, 사용자 ID: " + userId);
            return true;
        } else {
            System.out.println("WebSocket 연결 실패: 유효하지 않은 토큰");
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                               WebSocketHandler wsHandler, Exception exception) {
        // 필요 시 추가 로직 구현
    }

    private String extractTokenFromRequest(ServerHttpRequest request) {
        String token = null;
        if (request.getURI().getQuery() != null) {
            String query = request.getURI().getQuery();
            System.out.println("Received Query: " + query);  // 쿼리 스트링을 로그로 남김
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair[0].equals("token")) {
                    token = pair.length > 1 ? pair[1] : null;
                }
            }
        }
        if (token == null) {
            System.out.println("JWT 토큰이 요청에 포함되지 않았습니다.");
        } else {
            System.out.println("Extracted JWT Token: " + token);  // 토큰 로그 출력
        }
        return token;
    }
}
