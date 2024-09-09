package com.kedu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.dao.ReservationDAO;
import com.kedu.dto.ReservationDTO;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationDAO reservationDAO;
	
	// 음식점 예약 등록
	public void post(ReservationDTO dto) {
		reservationDAO.post(dto);
	}
	
	// 음식점 예약 삭제
	public void reserveDelete(int reservationId) {
		reservationDAO.reserveDelete(reservationId);
	}
}
