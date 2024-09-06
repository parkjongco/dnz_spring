package com.kedu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private long expiration = 10;

    private Algorithm algo;
    private JWTVerifier verifier;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.algo = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algo).build();
    }

    public String createToken(String id) {
        return JWT.create()  // JWT 생성
                .withSubject(id)  // 주제(Subject) 설정
                .withIssuedAt(new Date())  // 발행 시간 설정
                .withExpiresAt(new Date(System.currentTimeMillis() + (expiration * 1000)))  // 만료 시간 설정
                .sign(this.algo);  // 알고리즘을 사용하여 서명 및 토큰 생성
    }

    public DecodedJWT verifyToken(String token) {
        return verifier.verify(token);
    }

    public String getSUbject(String token) {
        DecodedJWT decodedJWT = this.verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public boolean isVerfied(String token) {
        try {
            this.verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


