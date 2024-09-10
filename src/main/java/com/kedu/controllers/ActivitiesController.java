package com.kedu.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kedu.dto.ActivitiesDTO;
import com.kedu.services.ActivitiesService;
import com.kedu.utils.JwtUtil;  // JWT 유틸리티 클래스

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    @Autowired
    private ActivitiesService activitiesService;

    @Autowired
    private JwtUtil jwtUtil;  // JWT 유틸리티 사용

    // 활동 기록 API
    @PostMapping("/log")
    public ResponseEntity<String> logActivity(@RequestBody ActivitiesDTO activity) {
        activitiesService.logActivity(activity);
        return ResponseEntity.ok("Activity logged successfully.");
    }

    // 사용자 활동 기록 조회 API
    @GetMapping("/user")
    public ResponseEntity<List<ActivitiesDTO>> getUserActivities(@RequestHeader("Authorization") String token) {
        // Authorization 헤더에서 Bearer 부분을 제거하고 토큰만 추출
        String jwtToken = token.replace("Bearer ", "");
        
        // JWT에서 userSeq 추출
        int userSeq = jwtUtil.getUserSeq(jwtToken); 

        // 활동 기록 가져오기
        List<ActivitiesDTO> activities = activitiesService.getUserActivities(userSeq);

        return ResponseEntity.ok(activities);
    }
}
