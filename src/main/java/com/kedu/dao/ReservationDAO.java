package com.kedu.dao;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
		// 예약 정보를 데이터베이스에 삽입
	    mybatis.insert("Reservation.insertReservation", dto);

	    // 좌석 상태를 업데이트 (차감)
	    updateSeatStatus(dto.getStoreSeq(), dto.getNumGuests());
	}

	// 좌석 상태 업데이트 (음식점 예약 등록 시)
    public void updateSeatStatus(int storeSeq, int numGuests) {
        Map<String, Object> params = new HashMap<>();
        params.put("storeSeq", storeSeq);
        params.put("numGuests", numGuests);
        mybatis.update("Reservation.updateSeatStatus", params);
    }
	
    // 음식점 예약 삭제 및 좌석 상태 업데이트
    public void deleteReservation(int reservationId, int storeSeq, int numGuests) {
        // 예약 삭제
        mybatis.delete("Reservation.deleteReservation", reservationId);
        
        // 좌석 상태 업데이트
        Map<String, Object> params = new HashMap<>();
        params.put("storeSeq", storeSeq);
        params.put("numGuests", numGuests);
        mybatis.update("Reservation.updateSeatStatusOnDelete", params);
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
    
 // 예약 상태와 좌석 상태 업데이트
    @Transactional
    public void updateReservationStatusAndSeat(int reservationId, String status) {
        updateReservationStatus(reservationId, status);
        
        // 상태가 "confirmed" 또는 "cancelled"인 경우에만 좌석 상태를 업데이트
        if ("confirmed".equals(status) || "cancelled".equals(status)) {
            mybatis.update("Reservation.updateSeatStatusAfterReservation", reservationId);
        }
    }


}
