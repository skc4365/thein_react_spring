package com.skc.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-validity}")
    private long accessTokenValidity; // 예: 30분 (1800000ms)

    // 1시간 단위를 설정 파일이나 상수로 주입 (1시간 = 3,600,000 ms)
//    @Value("${jwt.refresh-token-validity:3600000}") 
//    private long refreshTokenValidity;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String username, String role) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessTokenValidity))
                .signWith(key)
                .compact();
    }
    
    // [신규 추가] Refresh Token 생성 (1시간 유효, 굳이 Role 같은 민감 정보는 담지 않는 것이 보안상 좋습니다)
//    public String createRefreshToken(String username) {
//        Date now = new Date();
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(now)
//                .expiration(new Date(now.getTime() + refreshTokenValidity))
//                .signWith(key)
//                .compact();
//    }

    // 	토큰에서 Username 추출
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

// 토큰 검증
    public boolean validate(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
