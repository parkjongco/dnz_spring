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
		mybatis.insert("Replies.insertReply", repliesDTO);
	}
	

	// 리뷰 ID로 답글 개수 조회
    public int countRepliesByReviewId(int reviewId) {
        return mybatis.selectOne("Replies.countRepliesByReviewId", reviewId);
    }
	
    // storeSeq에 따른 답글 목록 조회
    public List<RepliesDTO> selectRepliesByStoreSeq(int storeSeq) {
        return mybatis.selectList("Replies.selectRepliesByStoreSeq", storeSeq);
    }
    

	// 리뷰 ID로 댓글 목록 조회
	public List<RepliesDTO> findRepliesByReviewId(int reviewId){
		return mybatis.selectList("Replies.findRepliesByReviewId", reviewId);
	}

}
