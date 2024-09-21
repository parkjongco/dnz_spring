package com.kedu.services;

import com.kedu.dao.RepliesDAO;
import com.kedu.dto.RepliesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepliesService {

    @Autowired
    RepliesDAO repliesDAO;

    public RepliesDTO addReply(RepliesDTO replyDTO) {
        // 해당 리뷰에 답글이 이미 있는지 확인
        int existingRepliesCount = repliesDAO.countRepliesByReviewId(replyDTO.getReviewId());
        
        if (existingRepliesCount > 0) {
            // 이미 답글이 존재하는 경우 null을 반환
            return null;
        }

        // 답글 삽입
        repliesDAO.insertReply(replyDTO);
        return replyDTO;
    }

   
}
