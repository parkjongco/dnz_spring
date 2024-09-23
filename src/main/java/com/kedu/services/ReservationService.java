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
	
	@Autowired
	private ReservationService reservationService;


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
			// 예약 상태가 기본적으로 'pending'
			if(dto.getStatus()== null) {
				dto.setStatus("pending");
			}
			System.out.println("서비스 레이어 상태 : " + dto.getStatus());
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

	public void updateReservation(ReservationDTO reservation) {
		reservationDAO.updateReservationStatus(reservation.getReservationId(), reservation.getStatus());
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
	    Timestamp lastNoshowTime = reservationDAO.getLastNoShowTime(userId);

	    // 취소 시간과 노쇼 시간 중 가장 최근의 시간을 사용
	    Timestamp latestEventTime = getLatestEventTime(lastCancellationTime, lastNoshowTime);

	    // 최근 이벤트(취소 또는 노쇼)가 없는 경우 예약 가능
	    if (latestEventTime == null) {
	        return true; // 노쇼나 취소 기록이 없으면 예약 가능
	    }

	    // 최근 이벤트가 3분 이내인지 확인
	    final long THREE_MINUTES_IN_MILLIS = 3 * 60 * 1000; // 3분 밀리초 상수
	    long timeDifference = System.currentTimeMillis() - latestEventTime.getTime();

	    return timeDifference >= THREE_MINUTES_IN_MILLIS; // 3분이 지났으면 예약 가능
	}

	// 마지막 취소가 없을 경우
	//		if (lastCancellationTime == null) {
	//			return true; // 제한 없이 예약 가능
	//		}

	// 가장 최근의 취소 또는 노쇼 시간을 반환하는 메서드
	private Timestamp getLatestEventTime(Timestamp cancellationTime, Timestamp noShowTime) {
		if(cancellationTime == null && noShowTime == null) return null; // 두 기록이 모두 없을 경우 null 반환
		if(cancellationTime == null) return noShowTime;
		if(noShowTime == null) return cancellationTime;
		return cancellationTime.after(noShowTime) ? cancellationTime : noShowTime;
	}

	// 당일 취소인 경우 3분 제한 적용
//	if (isToday(lastCancellationTime)) {
//		final long THREE_MINUTES_IN_MILLIS = 3 * 60 * 1000; // 3분 밀리초 상수
//		long timeDifference = System.currentTimeMillis() - lastCancellationTime.getTime();
//		if (timeDifference < THREE_MINUTES_IN_MILLIS) {
//			return false; // 3분 이내인 경우 예약 불가
//		}
//	}

	// 당일 취소가 아닌 경우 제한 없음
//	return true;
//}
//
//// 날짜가 당일인지 확인하는 메서드
//private boolean isToday(Timestamp lastCancellationTime) {
//	Calendar currentDate = Calendar.getInstance();
//	Calendar cancellationDate = Calendar.getInstance();
//	cancellationDate.setTimeInMillis(lastCancellationTime.getTime());
//
//	return currentDate.get(Calendar.YEAR) == cancellationDate.get(Calendar.YEAR) &&
//			currentDate.get(Calendar.DAY_OF_YEAR) == cancellationDate.get(Calendar.DAY_OF_YEAR);
//}

}
