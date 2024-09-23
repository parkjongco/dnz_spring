package com.kedu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.RepliesDTO;

@Repository
public class RepliesDAO {

	@Autowired
	private SqlSession mybatis;
	
	// 음식점 예약 등록
	public void insertReply(RepliesDTO repliesDTO) {
		mybatis.insert("Reservation.insert", repliesDTO);
	}
	
	// 리뷰 ID로 댓글 목록 조회
	public List<RepliesDTO> findRepliesByReviewId(int reviewId){
		return mybatis.selectList("Replies.findRepliesByReviewId", reviewId);
	}
}
