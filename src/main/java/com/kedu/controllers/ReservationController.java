package com.kedu.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
	public ResponseEntity<String> post(@RequestBody ReservationDTO dto, Authentication authentication) {
		// 사용자 ID 설정
		String userId = authentication.getName();
		dto.setUserId(userId);

		// 24시간 예약 제한 확인
		boolean canReserve = reservationService.canMakeReservation(userId);
		if (!canReserve) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("최근 24시간 이내 취소로 인해 예약이 불가능합니다.");
		}

		// 날짜 형식 변환 처리
		try {
			// 'yyyy-MM-dd' 형식의 날짜 문자열을 Timestamp로 변환
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp reservationDate = new Timestamp(dateFormat.parse(dto.getReservationDate().toString()).getTime());
			dto.setReservationDate(reservationDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("예약 날짜 형식이 잘못되었습니다.");
		}

		// 로그 출력 및 예약 처리
		System.out.println("userId: " + dto.getUserId());
		System.out.println("Reservation Date: " + dto.getReservationDate());

		// 예약 등록 서비스 호출
		reservationService.post(dto);

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
