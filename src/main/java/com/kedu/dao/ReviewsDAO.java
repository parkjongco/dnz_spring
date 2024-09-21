package com.kedu.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.ReviewsDTO;

@Repository
public class ReviewsDAO {

	@Autowired
	private SqlSession mybatis;
	// 리뷰 등록
	public void insertReview(ReviewsDTO dto) {
		mybatis.insert("Reviews.insertReview", dto);
	}

	// 예약 ID로 리뷰 조회
	public ReviewsDTO getReviewByReservationId(int reservationId) {
		return mybatis.selectOne("Reviews.getReviewByReservationId", reservationId);
	}

	// 리뷰 수정
	public void updateReview(ReviewsDTO dto) {
		mybatis.update("Reviews.updateReview", dto);
	}

	// 리뷰 삭제
	public void deleteReview(int reservationId) {
		mybatis.delete("Reviews.deleteReview", reservationId);
	}
}
