package com.kedu.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.dto.BookmarkDTO;
import com.kedu.services.BookmarkService;
import com.kedu.utils.JwtUtil;

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

    // 특정 사용자의 북마크 목록 가져오기
    @GetMapping("/list")
    public ResponseEntity<List<BookmarkDTO>> getBookmarkedStoresWithDetails(@RequestHeader("Authorization") String token) {
        String auth = token.substring(7); // "Bearer " 제거
        int userSeq = jwtUtil.getUserSeq(auth); // JWT에서 userSeq 추출
        List<BookmarkDTO> bookmarkedStores = bookmarkService.getBookmarkedStoresWithDetails(userSeq); // userSeq로 북마크된 storeSeq와 storeName 목록 가져오기
        return ResponseEntity.ok(bookmarkedStores);
    }

    // 특정 가게에 대한 북마크 상태 확인
    @GetMapping("/check/{storeSeq}")
    public ResponseEntity<Boolean> checkBookmark(@PathVariable int storeSeq, @RequestHeader("Authorization") String token) {
        int userSeq = jwtUtil.getUserSeq(token.substring(7)); // JWT에서 userSeq 추출
        boolean isBookmarked = bookmarkService.isBookmarked(userSeq, storeSeq);
        return ResponseEntity.ok(isBookmarked);
    }
    
 // 북마크 삭제
    @PostMapping("/remove")
    public ResponseEntity<String> removeBookmark(@RequestBody Map<String, Integer> data, @RequestHeader("Authorization") String token) {
        try {
            int storeSeq = data.get("storeSeq");
            String auth = token.substring(7); // "Bearer " 제거
            int userSeq = jwtUtil.getUserSeq(auth); // JWT에서 userSeq 추출

            bookmarkService.deleteBookmark(userSeq, storeSeq); // 북마크 삭제
            return ResponseEntity.ok("북마크 삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("북마크 삭제 중 오류 발생");
        }
    }
}
