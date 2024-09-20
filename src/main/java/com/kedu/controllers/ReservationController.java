package com.kedu.controllers;

import com.kedu.dto.ReservationDTO;
import com.kedu.services.ReservationService;
import com.kedu.utils.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	public ResponseEntity<String> post(@RequestBody ReservationDTO dto, Authentication authentication) {
	    // 사용자 ID 설정
	    String userId = authentication.getName();
	    dto.setUserId(userId);

	    // 예약 가능 여부 확인 (3분 제한)
	    boolean canReserve = reservationService.canMakeReservation(userId);
	    if (!canReserve) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("당일 취소로 인해 24시간 동안 예약이 불가능합니다.");
	    }

	    // 날짜 형식 변환 처리
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Timestamp reservationDate = new Timestamp(dateFormat.parse(dto.getReservationDate().toString()).getTime());
	        dto.setReservationDate(reservationDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("예약 날짜 형식이 잘못되었습니다.");
	    }

	    // 예약 등록 시도
	    try {
	        reservationService.post(dto);
	    } catch (Exception e) {
	        e.printStackTrace();  // 예외를 서버 로그에 기록
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("서버 내부 오류로 인해 예약 등록에 실패했습니다.");
	    }

	    return ResponseEntity.ok("예약이 성공적으로 등록되었습니다.");
	}


	// 사용자 예약 삭제 엔드포인트
	@DeleteMapping("/{reservationId}")
	public ResponseEntity<Void> deleteReservation(@PathVariable int reservationId, Authentication authentication) {
		String userId = authentication.getName();
		ReservationDTO reservation = reservationService.findReservationById(reservationId);

		// 사용자가 본인의 예약을 취소하려는 경우에만 삭제 허용
		if (reservation != null && reservation.getUserId().equals(userId)) {
			reservationService.deleteReservation(reservationId);
			reservationService.recordCancellation(userId);  // 취소 기록 저장
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	// 모든 예약 내역을 조회하는 엔드포인트
	@GetMapping("/user")
	public ResponseEntity<Map<String, Object>> getUserReservations(Authentication authentication) {
		// 사용자 ID를 추출
		String userId = authentication.getName();

		// 해당 사용자의 모든 예약 목록을 가져옴
		List<ReservationDTO> reservations = reservationService.getReservationsByUserId(userId);

		// 응답 데이터를 Map으로 구성
		Map<String, Object> response = new HashMap<>();
		response.put("userId", userId);
		response.put("reservations", reservations);

        return ResponseEntity.ok(response);
    }
	
    // 예약 상태 업데이트 엔드포인트
    @PutMapping("/{reservationId}")
    public ResponseEntity<Void> updateReservationStatus(
            @PathVariable int reservationId, 
            @RequestParam String status, 
            Authentication authentication) {

        String userId = authentication.getName();
        ReservationDTO reservation = reservationService.findReservationById(reservationId);

        // 예약이 존재하고 사용자가 해당 예약의 소유자인 경우에만 상태 변경 허용
        if (reservation != null && reservation.getUserId().equals(userId)) {
            reservation.setStatus(status); // 새로운 상태로 업데이트
            reservationService.updateReservation(reservation); // 상태 업데이트
            
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

	// 예약 가능 여부를 확인하는 API
	@GetMapping("/checkEligibility")
	public ResponseEntity<Map<String, Boolean>> checkReservationEligibility(Authentication authentication) {
		String userId = authentication.getName();
		boolean canReserve = reservationService.canMakeReservation(userId);

		Map<String, Boolean> response = new HashMap<>();
		response.put("canReserve", canReserve);

		return ResponseEntity.ok(response);
	}

}
