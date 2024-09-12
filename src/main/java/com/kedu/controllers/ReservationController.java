package com.kedu.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.dto.ReservationDTO;
import com.kedu.services.ReservationService;
import com.kedu.utils.JwtUtil;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/reservation")
public class ReservationController {


	@Autowired
    private ReservationService reservationService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private HttpSession session;
	
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
	
	// 음식점 예약 등록
	@PostMapping
	public ResponseEntity<Void> post (@RequestBody ReservationDTO dto) {
		System.out.println("Reservation Time: " + dto.getReservationTime());
	    System.out.println("Reservation Date: " + dto.getReservationDate());
	    System.out.println("Number of Guests: " + dto.getNumGuests());
		System.out.println("예약 등록 접근");
		reservationService.post(dto);
		return ResponseEntity.ok().build();
	}
		
//	 음식점 예약 삭제
//	@DeleteMapping	
//	public ResponseEntity<Void> reserveDelete(@PathVariable int id, @AuthenticationPrincipal UserDetails user){
//		String userId = user.getUsername();
//	ReservationDTO reservation = reservationService.findByReserveId(id);
//		
//		if(reservation != null && reservation.getUserId().equals(userId)) {
//			reservationService.reserveDelete(id);
//			// 성공적으로 삭제 되었을 시
//			return ResponseEntity.ok().build(); 
//		}else {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//		}		
//	}
	

	
}
