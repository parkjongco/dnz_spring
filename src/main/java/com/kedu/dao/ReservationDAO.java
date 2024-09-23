package com.kedu.dao;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.CancellationRecordDTO;
import com.kedu.dto.ReservationDTO;


@Repository
public class ReservationDAO {

	@Autowired
	private SqlSession mybatis;

	// 상태와 사용자 ID로 예약 목록 조회
	public List<ReservationDTO> findByStatusAndUser(String status, String userId) {
		// 두 개의 파라미터를 Map으로 묶어서 전달
		Map<String, Object> params = new HashMap<>();
		params.put("status", status);
		params.put("userId", userId);

		return mybatis.selectList("Reservation.findByStatusAndUser", params);
	}



	//	public List<ReservationDTO> findAllReservation() {
	//        return mybatis.selectList("Reservation.selectall");
	//    }




	// 음식점 예약 등록
	public void post(ReservationDTO dto) {
		mybatis.insert("Reservation.insert", dto);
	}

	// 음식점 예약 삭제
	public void deleteReservation(int reservationId) {
		mybatis.delete("Reservation.deleteReservation", reservationId);
	}
	
	// 예약 ID로 예약 정보 조회
	public ReservationDTO findReservationById(int reservationId) {
		return mybatis.selectOne("Reservation.findReservationById", reservationId);
	}

	// 사용자의 예약 내역 가져오기
	public List<ReservationDTO>getReservationsByUserId(String userId){
		return mybatis.selectList("Reservation.getReservationsByUserId", userId);
	}
	
	// 취소 기록 저장
    public void recordCancellation(CancellationRecordDTO cancellationRecordDTO) {
        mybatis.insert("Reservation.recordCancellation", cancellationRecordDTO);
    }
    
    // 사용자가 마지막으로 취소한 예약을 가져오기
    public ReservationDTO findLastCancelledReservationByUser(String userId) {
        return mybatis.selectOne("Reservation.findLastCancelledReservationByUser", userId);
    }

    // 사용자의 마지막 취소 시간을 가져오기
    public Timestamp getLastCancellationTime(String userId) {
        return mybatis.selectOne("Reservation.getLastCancellationTime", userId);
    }
    
    // 사용자의 마지막 노쇼 시간을 가져오기
    public Timestamp getLastNoShowTime(String userId) {
    	return mybatis.selectOne("Reservation.getLastNoShowTime", userId);
    }
    
    // 예약 상태 업데이트
    public void updateReservationStatus(int reservationId, String status) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("reservationId", reservationId);
    	params.put("status", status);
    	mybatis.update("Reservation.updateReservationStatus", params);
    }

}
