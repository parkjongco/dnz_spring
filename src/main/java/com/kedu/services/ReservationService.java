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
	

	
	// 음식점 예약 등록
	public void post(ReservationDTO dto) {
		reservationDAO.post(dto);
	}
	
	// 음식점 예약 삭제
	public void deleteReservation(int reservationId) {
		reservationDAO.deleteReservation(reservationId);
	}
	
	// 예약 Id로 예약 조회(삭제하기 위해)
	public ReservationDTO findReservationById(int reservationId) {
		return reservationDAO.findReservationById(reservationId);
	}
	
	// 사용자의 모든 예약 가져오기
	public List<ReservationDTO> getReservationsByUserId(String userId){
		return reservationDAO.getReservationsByUserId(userId);
	}

	public void updateReservation(ReservationDTO reservation) {
        reservationDAO.updateReservationStatus(reservation);
    }
	
}
