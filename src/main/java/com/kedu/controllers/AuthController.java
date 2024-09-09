package com.kedu.controllers;


import com.kedu.config.CustomException;
import com.kedu.dto.EmailVerificationsDTO;
import com.kedu.dto.MembersDTO;
import com.kedu.services.EmailVerificationService;
import com.kedu.services.MembersService;
import com.kedu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

        // 이메일 인증 요청
        @PostMapping("/requestEmailVerification/{email}")
        public ResponseEntity<String> requestEmailVerification(@PathVariable("email") String userEmail) {
            System.out.println(userEmail);
            try {
                // 이메일 인증 코드 생성
                String verificationCode = UUID.randomUUID().toString();

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
    public ResponseEntity<String> login(@RequestBody MembersDTO dto) {
        UserDetails storedMember = membersService.loadUserByUsername(dto.getUserId());

        if (storedMember == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // JWT 토큰 생성
        String token = jwtUtil.createToken(dto.getUserId());
        return ResponseEntity.ok(token);
    }
}
