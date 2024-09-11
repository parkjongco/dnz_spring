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

    private long expiration = 86400; // 토큰 만료 시간 24시간

    private Algorithm algo;
    private JWTVerifier verifier;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.algo = Algorithm.HMAC256(secret); // JWT 비밀키 설정
        this.verifier = JWT.require(algo).build(); // 검증기 생성
    }

    public String createToken(String id, int userSeq) {
        return JWT.create()  // JWT 생성
                .withSubject(id)  // 주제(Subject) 설정
                .withClaim("userSeq", userSeq)
                .withIssuedAt(new Date())  // 발행 시간 설정
                .withExpiresAt(new Date(System.currentTimeMillis() + (expiration * 1000)))  // 만료 시간 설정
                .sign(this.algo);  // 알고리즘을 사용하여 서명 및 토큰 생성
    }

    public DecodedJWT verifyToken(String token) {
        return verifier.verify(token); // 토큰 검증
    }

    public String getSUbject(String token) {
        DecodedJWT decodedJWT = this.verifier.verify(token); // 토큰에서 subject 추출
        return decodedJWT.getSubject();
    }

    public boolean isVerfied(String token) {
        try {
            this.verifier.verify(token); // 토큰 검증
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // JWT에서 userSeq를 클레임에서 추출
    public int getUserSeq(String token) {
        DecodedJWT decodedJWT = this.verifier.verify(token);
        return decodedJWT.getClaim("userSeq").asInt();  // userSeq를 정수로 반환
    }
}
