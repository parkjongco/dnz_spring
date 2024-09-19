package com.kedu.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.RepliesDTO;
import com.kedu.dto.ReservationDTO;

@Repository
public class RepliesDAO {

	@Autowired
	private SqlSession mybatis;
	
	// 음식점 예약 등록
	public void insertReply(RepliesDTO repliesDTO) {
		mybatis.insert("Reservation.insert", repliesDTO);
	}
	
}
