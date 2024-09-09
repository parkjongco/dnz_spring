package com.kedu.controllers;

import java.util.List;

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
    public ResponseEntity<List<ReservationDTO>> getAllReservations(
            Authentication authentication, 
            @RequestParam String status) {
    	
    	// JWT 토큰에서 사용자 ID 추출
        String userId = authentication.getName();
        
        // 사용자 ID와 예약 상태에 따른 예약 필터링
        List<ReservationDTO> reservations = reservationService.getReservationsByStatusAndUser(status, userId);
        return ResponseEntity.ok(reservations);
    }
	
}
