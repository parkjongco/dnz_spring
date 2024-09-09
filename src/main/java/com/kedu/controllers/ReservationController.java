package com.kedu.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.dto.ReservationDTO;
import com.kedu.services.ReservationService;
import com.kedu.utils.JwtUtil;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
    private ReservationService reservationService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	// 모든 예약 데이터를 가져오는 엔드포인트
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllReservations( // ResponseEntity<List<ReservationDTO>>
            Authentication authentication, 
            @RequestParam String status) {
    	
    	System.out.println("엔드포인트 진입");
    	// JWT 토큰에서 사용자 ID 추출
        String userId = authentication.getName();
        System.out.println(userId);
        
        // 사용자 ID와 예약 상태에 따른 예약 필터링
        List<ReservationDTO> reservations = reservationService.getReservationsByStatusAndUser(status, userId);
        
        // 응답 데이터를 Map으로 구성
        Map<String, Object> response = new HashMap<>();
        response.put("userName", userId);
        response.put("reservations", reservations);
        
        return ResponseEntity.ok(response);
    }
	
}
