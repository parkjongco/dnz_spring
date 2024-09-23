package com.kedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.dao.RepliesDAO;
import com.kedu.dto.RepliesDTO;

@Service
public class RepliesService {

    @Autowired
    RepliesDAO repliesDAO;

    public RepliesDTO addReply(RepliesDTO repliesDTO) {
        repliesDAO.insertReply(repliesDTO);
        return repliesDTO;
    }
    
    // 리뷰에 대한 댓글 목록 가져오기
    public List<RepliesDTO> getRepliesByReviewId(int reviewId){
    	return repliesDAO.findRepliesByReviewId(reviewId);
    }
   
}
