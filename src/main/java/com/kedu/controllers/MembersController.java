package com.kedu.controllers;

import com.kedu.dto.MembersDTO;
import com.kedu.services.MembersService;
import com.kedu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/members")
public class MembersController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MembersService membersService;

//    @GetMapping("/userProfile/{userId}")
//    public ResponseEntity<List<MembersDTO>> getUserProfile(@PathVariable String user_Id) {
//        System.out.println("여기뭐오긴하냐 씨발");
//        try {
//            // 사용자 ID를 기반으로 사용자 정보 가져오기
//            List<MembersDTO> userProfile = membersService.getUserProfile(user_Id);
//            System.out.println(userProfile.toString());
//            System.out.println(userProfile);
//
//            if (userProfile == null || userProfile.isEmpty()) {
//                // 사용자가 없을 때 404 Not Found 상태 코드 반환
//                return ResponseEntity.notFound().build();
//            }
//
//            // 사용자 정보가 있을 경우 200 OK와 함께 정보 반환
//            return ResponseEntity.ok(userProfile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/userProfile/{userId}")
    public ResponseEntity<MembersDTO> getUserProfile(@PathVariable String userId) {
        try {
            // 사용자 ID를 기반으로 사용자 정보 가져오기
            MembersDTO userProfile = membersService.selectById(userId);
            System.out.println(userProfile.toString());
            System.out.println(userProfile);

            if (userProfile == null ) {
                // 사용자가 없을 때 404 Not Found 상태 코드 반환
                return ResponseEntity.notFound().build();
            }

            // 사용자 정보가 있을 경우 200 OK와 함께 정보 반환
            return ResponseEntity.ok(userProfile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
 // MembersController.java
    @PostMapping("/updateProfileImage")
    public ResponseEntity<String> updateProfileImage(
            @RequestParam("profileImage") MultipartFile profileImage,
            @RequestHeader("Authorization") String token) {
        try {
            String userId = jwtUtil.getSubject(token.substring(7)); // JWT에서 사용자 ID 추출
            String imageUrl = membersService.saveProfileImage(userId, profileImage);
            return ResponseEntity.ok(imageUrl);  // 저장된 이미지 URL 반환
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating profile image");
        }
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateUserProfile(
            @RequestBody Map<String, Object> updatedFields,
            @RequestHeader("Authorization") String token) {
        try {
            // JWT 토큰에서 사용자 ID를 추출
            String userId = jwtUtil.getSubject(token.substring(7)); // 'Bearer ' 이후

            // 변경된 필드만 업데이트
            membersService.updateUserProfile(userId, updatedFields);

            return ResponseEntity.ok("Profile updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating profile");
        }
    }


    @DeleteMapping("/delete/{userId}/{userSeq}")

    public ResponseEntity<String> deleteAccount(@PathVariable String userId, @PathVariable String userSeq) {
        // 회원 존재 여부 확인
        MembersDTO id = membersService.selectById(userId);
        if (id == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자가 존재하지 않습니다.");
        }

        try {
            // 회원 탈퇴 로직
            membersService.deleteAccount(userId, userSeq);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
        }

        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }





}
