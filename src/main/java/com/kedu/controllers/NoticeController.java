package com.kedu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.kedu.dto.NoticeDTO;
import com.kedu.services.NoticeService;

@RestController
@RequestMapping("/notice")
public class NoticeController {

	
    @Autowired
    private NoticeService noticeService;

 // 일반 회원이 특정 식당의 공지사항 조회 엔드포인트
    @GetMapping("/store/{storeSeq}")
    public ResponseEntity<NoticeDTO> getNoticeByStore(@PathVariable("storeSeq") Long storeSeq) {
        NoticeDTO notice = noticeService.getNoticeByStoreId(storeSeq);
        if (notice != null) {
            return ResponseEntity.ok(notice);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    
    // 가게 공지사항 조회 엔드포인트
    @GetMapping
    public ResponseEntity<NoticeDTO> getNotice(Authentication authentication) {
        String userId = authentication.getName();
        NoticeDTO notice = noticeService.getNoticeByUserId(userId);
        if (notice != null) {
            return ResponseEntity.ok(notice);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

 // 가게 공지사항 추가 엔드포인트
    @PostMapping
    public ResponseEntity<Void> addNotice(Authentication authentication, @RequestBody NoticeDTO noticeDTO) {
        String userId = authentication.getName();
        noticeDTO.setUserId(userId);
        noticeService.insertNotice(noticeDTO);
        return ResponseEntity.ok().build();
    }

    // 가게 공지사항 수정 엔드포인트
    @PutMapping
    public ResponseEntity<Void> updateNotice(Authentication authentication, @RequestBody NoticeDTO noticeDTO) {
        String userId = authentication.getName();
        noticeDTO.setUserId(userId);
        noticeService.updateNotice(noticeDTO);
        return ResponseEntity.ok().build();
    }
    
}
