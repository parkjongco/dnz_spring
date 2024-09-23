package com.kedu.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void post(ReservationDTO dto) {
        try {
            // 예약 상태가 기본적으로 'pending'
            if (dto.getStatus() == null) {
                dto.setStatus("pending");
            }
            reservationDAO.post(dto);
        } catch (Exception e) {
            System.err.println("예약 등록 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("예약 등록 중 문제가 발생했습니다.", e);
        }
    }

    // 음식점 예약 삭제
    public void deleteReservation(int reservationId) {
        try {
            ReservationDTO reservation = reservationDAO.findReservationById(reservationId);
            if (reservation != null) {
                reservationDAO.deleteReservation(reservationId, reservation.getStoreSeq(), reservation.getNumGuests());
            } else {
                throw new RuntimeException("예약이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException("예약 삭제 중 문제가 발생했습니다.", e);
        }
    }

    // 예약 Id로 예약 조회(삭제하기 위해)
    public ReservationDTO findReservationById(int reservationId) {
        return reservationDAO.findReservationById(reservationId);
    }

    // 사용자의 모든 예약 가져오기
    public List<ReservationDTO> getReservationsByUserId(String userId) {
        return reservationDAO.getReservationsByUserId(userId);
    }

    // 예약 상태 업데이트
    public void updateReservation(ReservationDTO reservation) {
        reservationDAO.updateReservationStatusAndSeat(reservation.getReservationId(), reservation.getStatus());
    }

    // 취소 기록 저장
    public void recordCancellation(String userId) {
        CancellationRecordDTO cancellationRecordDTO = new CancellationRecordDTO();
        cancellationRecordDTO.setUserId(userId);
        cancellationRecordDTO.setCancellationTime(new Timestamp(System.currentTimeMillis()));

        try {
            reservationDAO.recordCancellation(cancellationRecordDTO);
        } catch (Exception e) {
            System.err.println("취소 기록 저장 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("취소 기록 저장 중 문제가 발생했습니다.", e);
        }
    }

    // 사용자가 예약 가능한지 확인 (오늘 예약을 취소했을 경우 3분 제한)
    public boolean canMakeReservation(String userId) {
        // 사용자의 마지막 취소된 예약을 가져옴
        ReservationDTO lastCancelledReservation = reservationDAO.findLastCancelledReservationByUser(userId);

        // 취소된 예약이 없으면 예약 가능
        if (lastCancelledReservation == null) {
            System.out.println("취소된 예약이 없으므로 예약 가능.");
            return true;
        }

        // 취소된 예약의 날짜가 오늘인 경우에만 3분 제한 적용
        if (isToday(lastCancelledReservation.getReservationDate())) {
            System.out.println("취소된 예약이 오늘입니다. 3분 제한을 적용합니다.");

            // 취소 후 3분 제한 적용
            final long THREE_MINUTES_IN_MILLIS = 3 * 60 * 1000; // 3분 밀리초 상수
            long timeDifference = System.currentTimeMillis() - lastCancelledReservation.getUpdatedAt().getTime();
            System.out.println("최근 취소 후 시간 차: " + timeDifference + "밀리초");

            // 3분 이내일 경우 제한
            if (timeDifference < THREE_MINUTES_IN_MILLIS) {
                System.out.println("오늘 예약 취소 후 3분 이내이므로 예약 불가.");
                return false; // 3분 이내인 경우 예약 불가
            }
        } else {
            System.out.println("취소된 예약이 오늘이 아니므로 제한 없이 예약 가능합니다.");
        }

        // 당일 예약 취소가 아니거나 3분이 지난 경우 제한 없이 예약 가능
        return true;
    }

    // 날짜가 당일인지 확인하는 메서드 (취소된 예약의 날짜 기준으로 확인)
    private boolean isToday(Timestamp reservationDate) {
        if (reservationDate == null) return false;

        Calendar currentDate = Calendar.getInstance();  // 현재 시간
        Calendar eventDate = Calendar.getInstance();    // 예약 날짜
        eventDate.setTimeInMillis(reservationDate.getTime());

        boolean isSameDay = currentDate.get(Calendar.YEAR) == eventDate.get(Calendar.YEAR) &&
                currentDate.get(Calendar.DAY_OF_YEAR) == eventDate.get(Calendar.DAY_OF_YEAR);

        return isSameDay;
    }
}
