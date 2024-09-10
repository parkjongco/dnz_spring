package com.kedu.handlers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kedu.utils.JwtUtil;

@Component
public class AlarmHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final JwtUtil jwtUtil;

    public AlarmHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // URL에서 쿼리 파라미터 추출 (토큰 검증)
        URI uri = session.getUri();
        Map<String, String> parameters = getQueryParams(uri.getQuery());
        String token = parameters.get("token");

        if (token != null && jwtUtil.isVerfied(token)) {
            String userId = jwtUtil.getSUbject(token);
            session.getAttributes().put("userId", userId);  // 세션에 사용자 ID 저장
            sessions.add(session);
            System.out.println("WebSocket 연결 성공 - 사용자 ID: " + userId);

            // 사용자에게 환영 메시지 전송
            String welcomeMessage = userId + "님 환영합니다!";
            session.sendMessage(new TextMessage(welcomeMessage));
            System.out.println("환영 메시지 전송: " + welcomeMessage);
        } else {
            System.out.println("WebSocket 연결 실패 - JWT 토큰이 유효하지 않음");
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket 연결 종료 - 세션 ID: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("수신된 메시지: " + message.getPayload());
    }

    public void sendNotification(String message) {
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                    System.out.println("메시지 전송 성공: " + message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendNotificationToUser(String userId, String message) {
        for (WebSocketSession session : sessions) {
            String sessionUserId = (String) session.getAttributes().get("userId");
            if (sessionUserId != null && sessionUserId.equals(userId)) {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(message));
                        System.out.println("메시지 전송 성공: " + message + " - 사용자: " + userId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 쿼리 문자열을 Map으로 변환하는 메서드
    private Map<String, String> getQueryParams(String query) {
        Map<String, String> map = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair.length > 1) {
                    map.put(pair[0], pair[1]);
                } else {
                    map.put(pair[0], "");
                }
            }
        }
        return map;
    }
}
