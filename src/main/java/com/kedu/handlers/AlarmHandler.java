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

import com.kedu.dto.ActivitiesDTO;
import com.kedu.services.ActivitiesService;
import com.kedu.utils.JwtUtil;

@Component
public class AlarmHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final JwtUtil jwtUtil;
    private final ActivitiesService activitiesService;  // ActivitiesService 주입

    public AlarmHandler(JwtUtil jwtUtil, ActivitiesService activitiesService) {
        this.jwtUtil = jwtUtil;
        this.activitiesService = activitiesService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        Map<String, String> parameters = getQueryParams(uri.getQuery());
        String token = parameters.get("token");

        if (token != null && jwtUtil.isVerfied(token)) {
            String userId = jwtUtil.getSubject(token);
            session.getAttributes().put("userId", userId);  
            sessions.add(session);
            System.out.println("WebSocket 연결 성공 - 사용자 ID: " + userId);

            // 로그인한 사용자에게 보낼 환영 메시지 생성
            String welcomeMessage = "로그인해주셔서 감사합니다, " + userId + "님!";
            
            // 환영 메시지 전송
            session.sendMessage(new TextMessage(welcomeMessage));

            // 활동 기록 남기기 - 환영 메시지 내용을 그대로 기록
            int userSeq = jwtUtil.getUserSeq(token); // 토큰에서 userSeq 추출
            logActivity(userSeq, "환영 메시지 전송", welcomeMessage);  // 환영 메시지를 그대로 전달
            
        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        System.out.println("수신된 메시지: " + message.getPayload());

        // 메시지 수신 시 활동 기록
        int userSeq = jwtUtil.getUserSeq(session.getAttributes().get("token").toString());
        logActivity(userSeq, "메시지 수신", userId + "님이 메시지를 수신했습니다.");

        // 받은 메시지에 따른 알림 전송 로직 추가
        String receivedMessage = message.getPayload();

        if (receivedMessage.equalsIgnoreCase("예약")) {
            sendNotificationToUser(userId, "예약이 완료되었습니다.");
            logActivity(userSeq, "예약", userId + "님이 예약을 완료했습니다.");
        } else if (receivedMessage.equalsIgnoreCase("등록")) {
            sendNotificationToUser(userId, "등록이 완료되었습니다.");
            logActivity(userSeq, "등록", userId + "님이 등록을 완료했습니다.");
        } else if (receivedMessage.equalsIgnoreCase("취소")) {
            sendNotificationToUser(userId, "취소가 완료되었습니다.");
            logActivity(userSeq, "취소", userId + "님이 취소를 완료했습니다.");
        } else {
            sendNotificationToUser(userId, "알 수 없는 명령어입니다: " + receivedMessage);
            logActivity(userSeq, "알 수 없는 명령", userId + "님이 알 수 없는 명령을 입력했습니다: " + receivedMessage);
        }
    }

    // 모든 사용자에게 알림 전송
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

    // 특정 사용자에게 알림 전송
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

    // 활동 기록을 위한 헬퍼 메서드
    private void logActivity(int userSeq, String activityType, String description) {
        ActivitiesDTO activity = new ActivitiesDTO();
        activity.setUserSeq(userSeq);
        activity.setActivityType(activityType);
        activity.setActivityDescription(description);
        activitiesService.logActivity(activity);  // 활동 기록 서비스 호출
    }

    // 쿼리 파라미터 추출 메서드
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
