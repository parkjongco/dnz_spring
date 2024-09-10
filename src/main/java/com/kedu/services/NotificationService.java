package com.kedu.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.handlers.AlarmHandler;

@Service
public class NotificationService {

    @Autowired
    private AlarmHandler alarmHandler;

    // 사용자별 알림 카운트를 저장하는 Map 추가
    private final Map<String, Integer> userNotifications = new HashMap<>();

    // 사용자의 현재 알림 카운트 반환
    public int getNotificationCount(String userId) {
        return userNotifications.getOrDefault(userId, 0);
    }

    // 사용자의 알림 카운트를 증가시키는 메서드
    public void incrementNotificationCount(String userId) {
        userNotifications.put(userId, getNotificationCount(userId) + 1);
    }

    // 사용자의 알림 카운트를 리셋하는 메서드
    public void resetNotificationCount(String userId) {
        userNotifications.put(userId, 0);
    }

    // 기존 알림 전송 로직 (유지)
    public void sendActivityNotification(String message) {
        if (message == null || message.isEmpty()) {
            System.out.println("경고: 빈 메시지가 전송될 수 있습니다.");
            return;
        }
        alarmHandler.sendNotification(message); // AlarmHandler의 sendNotification 호출
    }
}
