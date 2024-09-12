package com.kedu.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kedu.dto.ActivitiesDTO;
import com.kedu.services.ActivitiesService;
import com.kedu.utils.JwtUtil;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    @Autowired
    private ActivitiesService activitiesService;

    @Autowired
    private JwtUtil jwtUtil;

    // 새로운 활동을 기록
    @PostMapping("/log")
    public ResponseEntity<String> logActivity(@RequestBody ActivitiesDTO activity) {
        activity.setIsRead(0); // 기본적으로 읽지 않은 상태로 기록
        int activityId = activitiesService.logActivity(activity);
        return ResponseEntity.ok("Activity logged successfully. ID: " + activityId);
    }

    // 사용자 활동 기록 조회
    @GetMapping("/user")
    public ResponseEntity<List<ActivitiesDTO>> getUserActivities(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        int userSeq = jwtUtil.getUserSeq(jwtToken);
        List<ActivitiesDTO> activities = activitiesService.getUserActivities(userSeq);
        return ResponseEntity.ok(activities);
    }

    // 읽지 않은 활동 기록 조회
    @GetMapping("/unread")
    public ResponseEntity<List<ActivitiesDTO>> getUnreadNotifications(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        int userSeq = jwtUtil.getUserSeq(jwtToken);
        List<ActivitiesDTO> unreadNotifications = activitiesService.getUnreadActivities(userSeq);
        return ResponseEntity.ok(unreadNotifications);
    }

    // 모든 활동을 읽음 상태로 업데이트
    @PostMapping("/markAsRead")
    public ResponseEntity<Map<String, String>> markActivitiesAsRead(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        int userSeq = jwtUtil.getUserSeq(jwtToken);
        activitiesService.markActivitiesAsReadByUserSeq(userSeq);

        Map<String, String> response = new HashMap<>();
        response.put("message", "사용자의 모든 활동이 읽음 처리되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 개별 활동을 읽음 상태로 업데이트
    @PostMapping("/markAsRead/{activityId}")
    public ResponseEntity<Map<String, String>> markActivityAsRead(
        @RequestHeader("Authorization") String token,
        @PathVariable int activityId) {
        
        String jwtToken = token.replace("Bearer ", "");
        int userSeq = jwtUtil.getUserSeq(jwtToken);

        // 개별 활동 읽음 처리
        boolean isUpdated = activitiesService.markActivityAsRead(userSeq, activityId);
        
        Map<String, String> response = new HashMap<>();
        if (isUpdated) {
            response.put("message", "활동이 성공적으로 읽음 처리되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "활동을 읽음 처리하는 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
