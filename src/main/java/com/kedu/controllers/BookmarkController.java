package com.kedu.controllers;

import com.kedu.services.BookmarkService;
import com.kedu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private JwtUtil jwtUtil;

    // 북마크 추가/삭제
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleBookmark(@RequestBody Map<String, Integer> data, @RequestHeader Map<String, String> headers) {
        int storeSeq = data.get("storeSeq");
        String auth = headers.get("authorization").substring(7); // Bearer 토큰에서 "Bearer " 제거
        int userSeq = jwtUtil.getUserSeq(auth); // JWT에서 userSeq 추출

        try {
            boolean isBookmarked = bookmarkService.isBookmarked(userSeq, storeSeq);
            if (isBookmarked) {
                bookmarkService.deleteBookmark(userSeq, storeSeq);
                return ResponseEntity.ok("북마크 해제 성공");
            } else {
                bookmarkService.insertBookmark(userSeq, storeSeq);
                return ResponseEntity.ok("북마크 추가 성공");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("북마크 처리 중 오류 발생");
        }
    }

    // 특정 가게에 대한 북마크 상태 확인
    @GetMapping("/check/{storeSeq}")
    public ResponseEntity<Boolean> checkBookmark(@PathVariable int storeSeq, @RequestHeader("Authorization") String token) {
        int userSeq = jwtUtil.getUserSeq(token.substring(7)); // JWT에서 userSeq 추출
        boolean isBookmarked = bookmarkService.isBookmarked(userSeq, storeSeq);
        return ResponseEntity.ok(isBookmarked);
    }
}
