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
    public ResponseEntity<Map<String, Object>> getAllReservations(Authentication authentication,
                                                                   @RequestParam String status) {
        String userId = authentication.getName();
        List<ReservationDTO> reservations = reservationService.getReservationsByStatusAndUser(status, userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userName", userId);
        response.put("reservations", reservations);

        return ResponseEntity.ok(response);
    }

    // 음식점 예약 등록
    @PostMapping
    public ResponseEntity<String> post(@RequestBody ReservationDTO dto, Authentication authentication) {
        String userId = authentication.getName();
        dto.setUserId(userId);
        dto.setStatus("pending");

        // 예약 가능 여부 확인 (3분 제한)
        boolean canReserve = reservationService.canMakeReservation(userId);
        if (!canReserve) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("최근 취소로 인해 3분 동안 예약이 제한됩니다.");
        }

        // 날짜 및 시간 형식 변환 처리
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateTimeString = dto.getReservationDate() + " " + dto.getReservationTime();
            Timestamp reservationDateTime = new Timestamp(dateTimeFormat.parse(dateTimeString).getTime());
            dto.setReservationDate(reservationDateTime);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("예약 날짜 및 시간 형식이 잘못되었습니다.");
        }

        // 예약 등록 시도
        try {
            reservationService.post(dto);
        } catch (Exception e) {
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

        if (reservation != null && reservation.getUserId().equals(userId)) {
            reservationService.deleteReservation(reservationId);

            // 당일 예약 취소일 경우에만 취소 기록 남기기
            reservationService.recordCancellation(userId, reservation.getReservationDate());

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // 모든 예약 내역을 조회하는 엔드포인트
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUserReservations(Authentication authentication) {
        String userId = authentication.getName();
        List<ReservationDTO> reservations = reservationService.getReservationsByUserId(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("reservations", reservations);

        return ResponseEntity.ok(response);
    }

    // 예약 상태 업데이트 엔드포인트
    @PutMapping("/{reservationId}")
    public ResponseEntity<Void> updateReservationStatus(@PathVariable int reservationId,
                                                        @RequestParam String status,
                                                        Authentication authentication) {
        String userId = authentication.getName();
        ReservationDTO reservation = reservationService.findReservationById(reservationId);

        if (reservation != null && reservation.getUserId().equals(userId)) {
            reservation.setStatus(status);
            reservationService.updateReservation(reservation);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // 예약 가능 여부 확인 API
    @GetMapping("/canReserve")
    public ResponseEntity<Map<String, Boolean>> canReserve(Authentication authentication) {
        String userId = authentication.getName();
        boolean canReserve = reservationService.canMakeReservation(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("canReserve", canReserve);

        return ResponseEntity.ok(response);
    }
}
