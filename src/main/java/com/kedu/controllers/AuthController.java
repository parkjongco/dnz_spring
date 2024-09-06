package com.kedu.controllers;

import com.kedu.config.CustomException;
import com.kedu.dto.MembersDTO;
import com.kedu.services.MembersService;
import com.kedu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    MembersService membersService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //회원가입
    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody MembersDTO dto) {
        try {
            membersService.registerUser(dto);
            return ResponseEntity.ok().build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MembersDTO dto) {
        UserDetails storedMember = membersService.loadUserByUsername(dto.getUserId());

        if (storedMember == null) {
            // 사용자 정보가 없거나 비밀번호가 일치하지 않을 경우
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // JWT 토큰 생성
        String token = jwtUtil.createToken(dto.getUserId());
        return ResponseEntity.ok(token);
    }



}
