package com.kedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.dao.ReservationDAO;
import com.kedu.dto.ReservationDTO;

@Service
public class ReservationService {

	@Autowired
	ReservationDAO reservationDAO;
	
	
	// 상태와 사용자 ID로 예약 목록 조회
    public List<ReservationDTO> getReservationsByStatusAndUser(String status, String userId) {
        return reservationDAO.findByStatusAndUser(status, userId);
    }
	
//	// 모든 예약 데이터를 가져오는 서비스 메소드
//    public List<ReservationDTO> getAllReservations() {
//        return reservationDAO.findAllReservation();
//    }
	
}
