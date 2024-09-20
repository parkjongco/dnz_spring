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

	//	// 모든 예약 데이터를 가져오는 서비스 메소드
	//    public List<ReservationDTO> getAllReservations() {
	//        return reservationDAO.findAllReservation();
	//    }



	// 음식점 예약 등록
	public void post(ReservationDTO dto) {
		try {
			reservationDAO.post(dto);
		} catch (Exception e) {
			// 예외 발생 시 로그 출력 및 추가 처리
			System.err.println("예약 등록 중 오류 발생: " + e.getMessage());
			throw new RuntimeException("예약 등록 중 문제가 발생했습니다.", e);
		}
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

	// 취소 기록 저장
	public void recordCancellation(String userId) {
		CancellationRecordDTO cancellationRecordDTO = new CancellationRecordDTO();
		cancellationRecordDTO.setUserId(userId);
		cancellationRecordDTO.setCancellationTime(new Timestamp(System.currentTimeMillis()));

		try {
			reservationDAO.recordCancellation(cancellationRecordDTO);
		} catch (Exception e) {
			// 예외 발생 시 로그 출력 및 추가 처리
			System.err.println("취소 기록 저장 중 오류 발생: " + e.getMessage());
			throw new RuntimeException("취소 기록 저장 중 문제가 발생했습니다.", e);
		}
	}

	// 사용자가 예약 가능한지 확인 (당일 취소 시 3분 제한)
	public boolean canMakeReservation(String userId) {
		Timestamp lastCancellationTime = reservationDAO.getLastCancellationTime(userId);

		// 마지막 취소가 없을 경우
		if (lastCancellationTime == null) {
			return true; // 제한 없이 예약 가능
		}

		// 당일 취소인 경우 3분 제한 적용
		if (isToday(lastCancellationTime)) {
			final long THREE_MINUTES_IN_MILLIS = 3 * 60 * 1000; // 3분 밀리초 상수
			long timeDifference = System.currentTimeMillis() - lastCancellationTime.getTime();
			if (timeDifference < THREE_MINUTES_IN_MILLIS) {
				return false; // 3분 이내인 경우 예약 불가
			}
		}

		// 당일 취소가 아닌 경우 제한 없음
		return true;
	}

	 // 날짜가 당일인지 확인하는 메서드
    private boolean isToday(Timestamp lastCancellationTime) {
        Calendar currentDate = Calendar.getInstance();
        Calendar cancellationDate = Calendar.getInstance();
        cancellationDate.setTimeInMillis(lastCancellationTime.getTime());

        return currentDate.get(Calendar.YEAR) == cancellationDate.get(Calendar.YEAR) &&
               currentDate.get(Calendar.DAY_OF_YEAR) == cancellationDate.get(Calendar.DAY_OF_YEAR);
    }

}
