package com.kedu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.dao.ReviewsDAO;
import com.kedu.dto.ReviewsDTO;

@Service
public class ReviewsService {
	
	@Autowired
	private ReviewsDAO reviewsDAO;
	
	// 리뷰 등록
	public void submitReview(ReviewsDTO dto) {
		reviewsDAO.insertReview(dto);
	}
	
	// 해당 가게에 자신이 작성한 리뷰 확인
	public ReviewsDTO getReviewByReservationId(int reservationId) {
		return reviewsDAO.getReviewByReservationId(reservationId);
	}
	
	 // 리뷰 수정
    public void updateReview(ReviewsDTO dto) {
        reviewsDAO.updateReview(dto);
    }

    // 리뷰 삭제
    public void deleteReview(int reservationId) {
        reviewsDAO.deleteReview(reservationId);
    }
}
