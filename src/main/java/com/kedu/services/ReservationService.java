package com.kedu.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.dao.ReservationDAO;
import com.kedu.dto.CancellationRecordDTO;
import com.kedu.dto.ReservationDTO;

@Service
public class ReservationService {

    @Autowired
    private ReservationDAO reservationDAO;

    // 상태와 사용자 ID로 예약 목록 조회
    public List<ReservationDTO> getReservationsByStatusAndUser(String status, String userId) {
        return reservationDAO.findByStatusAndUser(status, userId);
    }

    // 음식점 예약 등록
    public void post(ReservationDTO dto) {
        try {
            if (dto.getStatus() == null) {
                dto.setStatus("pending");
            }
            reservationDAO.post(dto);
        } catch (Exception e) {
            throw new RuntimeException("예약 등록 중 문제가 발생했습니다.", e);
        }
    }

    // 음식점 예약 삭제
    public void deleteReservation(int reservationId) {
        reservationDAO.deleteReservation(reservationId);
    }

    // 예약 Id로 예약 조회
    public ReservationDTO findReservationById(int reservationId) {
        return reservationDAO.findReservationById(reservationId);
    }

    // 사용자의 모든 예약 가져오기
    public List<ReservationDTO> getReservationsByUserId(String userId) {
        return reservationDAO.getReservationsByUserId(userId);
    }

    // 예약 상태 업데이트
    public void updateReservation(ReservationDTO reservation) {
        reservationDAO.updateReservationStatus(reservation.getReservationId(), reservation.getStatus());
    }

    // 당일 예약 취소 기록 저장
    public void recordCancellation(String userId, Timestamp reservationDate) {
        if (isToday(reservationDate)) {
            CancellationRecordDTO cancellationRecordDTO = new CancellationRecordDTO();
            cancellationRecordDTO.setUserId(userId);
            cancellationRecordDTO.setCancellationTime(new Timestamp(System.currentTimeMillis()));

            try {
                reservationDAO.recordCancellation(cancellationRecordDTO);
            } catch (Exception e) {
                throw new RuntimeException("취소 기록 저장 중 문제가 발생했습니다.", e);
            }
        }
    }

    // 사용자가 예약 가능한지 확인 (오늘 예약을 취소했을 경우 3분 제한)
    public boolean canMakeReservation(String userId) {
        Timestamp lastCancellationTime = reservationDAO.getLastCancellationTime(userId);

        if (lastCancellationTime == null) {
            return true;
        }

        final long THREE_MINUTES_IN_MILLIS = 3 * 60 * 1000;
        long timeDifference = System.currentTimeMillis() - lastCancellationTime.getTime();

        if (timeDifference < THREE_MINUTES_IN_MILLIS) {
            return false; // 3분 이내인 경우 예약 불가
        }

        return true;
    }

    // 날짜가 당일인지 확인하는 메서드
    private boolean isToday(Timestamp reservationDate) {
        Calendar currentDate = Calendar.getInstance();
        Calendar eventDate = Calendar.getInstance();
        eventDate.setTimeInMillis(reservationDate.getTime());

        return currentDate.get(Calendar.YEAR) == eventDate.get(Calendar.YEAR)
                && currentDate.get(Calendar.DAY_OF_YEAR) == eventDate.get(Calendar.DAY_OF_YEAR);
    }
}
