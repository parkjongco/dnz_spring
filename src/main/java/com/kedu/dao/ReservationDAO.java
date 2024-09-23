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

    public List<ReservationDTO> findByStatusAndUser(String status, String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("userId", userId);

        return mybatis.selectList("Reservation.findByStatusAndUser", params);
    }

    public void post(ReservationDTO dto) {
        mybatis.insert("Reservation.insert", dto);
    }

    public void deleteReservation(int reservationId) {
        mybatis.delete("Reservation.deleteReservation", reservationId);
    }

    public ReservationDTO findReservationById(int reservationId) {
        return mybatis.selectOne("Reservation.findReservationById", reservationId);
    }

    public List<ReservationDTO> getReservationsByUserId(String userId) {
        return mybatis.selectList("Reservation.getReservationsByUserId", userId);
    }

    public void recordCancellation(CancellationRecordDTO cancellationRecordDTO) {
        mybatis.insert("Reservation.recordCancellation", cancellationRecordDTO);
    }

    public Timestamp getLastCancellationTime(String userId) {
        return mybatis.selectOne("Reservation.getLastCancellationTime", userId);
    }

    public void updateReservationStatus(int reservationId, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("reservationId", reservationId);
        params.put("status", status);
        mybatis.update("Reservation.updateReservationStatus", params);
    }
}
