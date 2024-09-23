package com.kedu.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(savedReply);
    }

    // 다른 Replies 관련 엔드포인트들 추가 가능...
    
    // 리뷰에 대한 답글 목록 가져오는 앤드포인트
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<RepliesDTO>> getRepliesByReviewId(@PathVariable int reviewId) {
        List<RepliesDTO> replies = repliesService.getRepliesByReviewId(reviewId);
        return ResponseEntity.ok(replies); // 데이터가 없을 경우 빈 리스트 반환
    }
}
