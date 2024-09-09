package com.kedu.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.ReservationDTO;

@Repository
public class ReservationDAO {
	
	@Autowired
	private SqlSession mybatis;
	
	
	// 음식점 예약 등록
	public void post(ReservationDTO dto) {
		mybatis.insert("Reservation.insert", dto);
	}

	// 음식점 예약 삭제
	public void reserveDelete(int reservationId) {
		mybatis.delete("Reservation.delete", reservationId);
	}
}
