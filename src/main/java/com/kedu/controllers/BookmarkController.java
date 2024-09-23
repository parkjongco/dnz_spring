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

    @PostMapping("/add")
    public ResponseEntity<String> addBookmark(@RequestBody Map<String, Integer> data, @RequestHeader Map<String, String> headers) {
        int storeSeq = data.get("storeSeq");
        String auth = headers.get("authorization").substring(7);

        int userseq = jwtUtil.getUserSeq(auth);
        System.out.println("userSeq" + userseq +"storeSeq" + storeSeq );

        try {
//             북마크 추가 로직
            bookmarkService.insertBookmark(userseq ,storeSeq);
            return ResponseEntity.ok("북마크 추가 성공");
        } catch (Exception e) {
            e.printStackTrace();
//             예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("북마크 추가 중 오류가 발생했습니다.");
        }
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<?> getUserBookmarks(@PathVariable String userId) {
//        try {
//            // userId로 userSeq 가져오기
//            int userseq = bookmarkService.getUserSeqByUserId(userId);
//            if (userseq == -1) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
//            }
//
//            // 해당 userSeq로 북마크 리스트 가져오기
//            return ResponseEntity.ok(bookmarkService.getBookmarksByUserSeq(userseq));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("북마크 조회 중 오류가 발생했습니다.");
//        }
//    }
}
