package com.kedu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.dto.ReservationDTO;
import com.kedu.services.ReservationService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
		
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private HttpSession session;
	
	
	// 음식점 예약 등록
	@PostMapping
	public ResponseEntity<Void> post (@RequestBody ReservationDTO dto) {
		System.out.println("예약 등록 접근");
		reservationService.post(dto);
		return ResponseEntity.ok().build();
	}
		
//	 음식점 예약 삭제
//	@DeleteMapping	
//	public ResponseEntity<Void> reserveDelete(@PathVariable int id, @AuthenticationPrincipal UserDetails user){
//		String userId = user.getUsername();
//		ReservationDTO reservation = reservationService.findByReserveId(id);
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
