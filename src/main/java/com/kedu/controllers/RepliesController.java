package com.kedu.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.kedu.dto.RepliesDTO;
import com.kedu.services.RepliesService;

@RestController
@RequestMapping("/replies")
public class RepliesController {

    @Autowired
    private RepliesService repliesService;

 // 리뷰에 대한 답글 추가 엔드포인트
    @PostMapping("/{reviewId}")
    public ResponseEntity<RepliesDTO> addReply(@PathVariable int reviewId,
                                             @RequestBody RepliesDTO replyDTO,
                                             Authentication authentication) {
        String userId = authentication.getName();
        replyDTO.setReviewId(reviewId);
        replyDTO.setUserId(userId);
        
        RepliesDTO savedReply = repliesService.addReply(replyDTO);
        if (savedReply == null) {
            // 이미 답글이 존재하는 경우, 409 Conflict 상태를 반환
            return ResponseEntity.status(409).build();
        }
        
        return ResponseEntity.ok(savedReply);
    }

    // 다른 Replies 관련 엔드포인트들 추가 가능...
}
