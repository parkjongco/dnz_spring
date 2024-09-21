package com.kedu.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.dto.RepliesDTO;
import com.kedu.dto.ReviewsDTO;
import com.kedu.services.RepliesService;
import com.kedu.services.ReviewsService;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {
	
	@Autowired
	private ReviewsService reviewsService;
	
	@Autowired
	private RepliesService repliesService;
	
	@PostMapping
	public ResponseEntity<String> submitReview(@RequestBody ReviewsDTO dto, Authentication authentication ){
		
		String userId = authentication.getName();
		dto.setUserId(userId);
		
		try {
			System.out.println(dto.getUserId());
			System.out.println(dto.getReviewText());
			System.out.println(dto.getRating());
			reviewsService.submitReview(dto);
			return ResponseEntity.ok("리뷰 작성 성공");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("리뷰 제출 실패");
		}
	}
	
	@GetMapping("/{reservationId}")
	public ResponseEntity<ReviewsDTO> getReviewByReservationId(@PathVariable int reservationId){
		ReviewsDTO dto = reviewsService.getReviewByReservationId(reservationId);
		if(dto != null) {
			return ResponseEntity.ok(dto);
		}else {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	 // 리뷰 수정
    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReview(@PathVariable int reservationId, @RequestBody ReviewsDTO dto) {
        try {
            dto.setReservationId(reservationId);
            reviewsService.updateReview(dto);
            return ResponseEntity.ok("리뷰가 성공적으로 수정");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("리뷰 수정에 실패");
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReview(@PathVariable int reservationId) {
        try {
            reviewsService.deleteReview(reservationId);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("리뷰 삭제에 실패");
        }
    }
    
 // 가게에 대한 모든 리뷰와 관련된 답글 조회
    @GetMapping("/store/{storeSeq}")
    public ResponseEntity<Map<String, Object>> getReviewsByStoreSeq(@PathVariable int storeSeq) {
        try {
            List<ReviewsDTO> reviews = reviewsService.getReviewsByStoreSeq(storeSeq);
            List<RepliesDTO> replies = repliesService.getRepliesByStoreSeq(storeSeq);

            Map<String, Object> response = new HashMap<>();
            response.put("reviews", reviews);
            response.put("replies", replies);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
