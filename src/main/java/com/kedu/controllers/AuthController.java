package com.kedu.controllers;


import com.kedu.config.CustomException;
import com.kedu.dto.ActivitiesDTO;
import com.kedu.dto.EmailVerificationsDTO;
import com.kedu.dto.MembersDTO;
import com.kedu.services.ActivitiesService;
import com.kedu.services.EmailVerificationService;
import com.kedu.services.MembersService;
import com.kedu.services.NotificationService;
import com.kedu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MembersService membersService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private NotificationService notificationService;  // 알림 서비스 주입

    @Autowired
    private ActivitiesService activitiesService;  // 활동 서비스 주입

    // 이메일 인증 요청
    @PostMapping("/requestEmailVerification/{email}")
    public ResponseEntity<String> requestEmailVerification(@PathVariable("email") String userEmail) {
        System.out.println(userEmail);
        try {

            // 이메일 인증 코드 생성
            String verificationCode = UUID.randomUUID().toString().substring(0, 8);

            // 이메일 인증 정보 저장
            EmailVerificationsDTO emailVerificationDTO = new EmailVerificationsDTO();
            emailVerificationDTO.setUserEmail(userEmail);
            emailVerificationDTO.setVerificationCode(verificationCode);
            emailVerificationService.saveVerification(emailVerificationDTO);

            // 이메일 전송
            emailVerificationService.sendVerificationEmail(userEmail, verificationCode);

            return ResponseEntity.ok("이메일 인증 코드가 전송되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    // 이메일 인증
    @PostMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailVerificationsDTO verificationDTO) {
        try {
            System.out.println(verificationDTO.getUserEmail() + " :" + verificationDTO.getVerificationCode());
            boolean isVerified = emailVerificationService.verifyCode(verificationDTO.getUserEmail(), verificationDTO.getVerificationCode());

            if (isVerified) {
                return ResponseEntity.ok("verified");
            } else {
                return ResponseEntity.badRequest().body("유효하지 않은 인증 코드입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    // 회원가입
    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody MembersDTO dto) {
        try {

            // 이메일 인증 확인
            boolean isEmailVerified = emailVerificationService.isEmailVerified(dto.getUserEmail());
            if (!isEmailVerified) {
                return ResponseEntity.badRequest().body("이메일 인증이 완료되지 않았습니다.");
            }
            // 회원 정보 저장 (미완료 상태)
            membersService.registerUser(dto);

            // 회원가입이 완료되었다는 메시지 전송
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MembersDTO dto) {
        // 사용자 정보 조회 (Spring Security의 UserDetails 사용)
        UserDetails storedMember = membersService.loadUserByUsername(dto.getUserId());

        // 사용자 정보가 없을 경우
        if (storedMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }

        // UserDetails에서 사용자 ID를 추출
        String userId = storedMember.getUsername();

        // 사용자 ID로 사용자 정보 조회
        MembersDTO mdto = membersService.selectById(userId);

        // 비밀번호가 일치하지 않는 경우
        if (!passwordEncoder.matches(dto.getUserPw(), mdto.getUserPw())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }

        // JWT 토큰 생성 (userId와 userSeq를 함께 사용하여 토큰 생성)
        String token = jwtUtil.createToken(mdto.getUserId(), mdto.getUserSeq());

        // 로그인 성공 후 실시간 알림 전송
        String notificationMessage = "User " + mdto.getUserId() + " has logged in!";
        notificationService.sendActivityNotification(notificationMessage);

        // 알림 카운트 가져오기
        int notificationCount = notificationService.getNotificationCount(mdto.getUserId());

        // 활동 기록
        ActivitiesDTO activity = new ActivitiesDTO();
        activity.setUserSeq(mdto.getUserSeq());
        activity.setActivityType("로그인");
        activity.setActivityDescription(mdto.getUserId() + "님이 로그인했습니다.");
        activitiesService.logActivity(activity);

        // 토큰과 사용자 ID, 알림 카운트 반환
        return ResponseEntity.ok(Map.of("token", token, "userId", mdto.getUserId(), "notificationCount", notificationCount));
    }

    @PostMapping("/findPassword")
    public ResponseEntity<String> resetPassword(@RequestBody MembersDTO dto) {
        try {
            System.out.println(dto.getUserEmail() + ":" + dto.getUserId());

            // 이메일과 사용자 아이디가 정상적으로 전달됐는지 확인
            if (dto.getUserEmail() == null || dto.getUserId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일과 아이디는 필수입니다.");
            }

            // 비밀번호 재설정 서비스 호출
            boolean success = emailVerificationService.resetPassword( dto.getUserId(),dto.getUserEmail());
            if (success) {
                return ResponseEntity.ok("임시 비밀번호가 이메일로 전송되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            // 서버 내부 오류 발생 시
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
        }
    }




}
