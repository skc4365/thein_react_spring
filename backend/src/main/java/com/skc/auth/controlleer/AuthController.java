package com.skc.auth.controlleer;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skc.auth.dto.LoginRequest;
import com.skc.entity.User;
import com.skc.jwt.JwtProvider;
import com.skc.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(user.getUsername(), user.getRole());

        // TODO: refreshToken 생성 후 HttpOnly 쿠키로 내려주기
//         Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
//         refreshCookie.setHttpOnly(true);
//         refreshCookie.setPath("/api/auth/refresh");
//         response.addCookie(refreshCookie);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "username", user.getUsername(),
                "name", user.getName(),
                "role", user.getRole()
        ));
    }
}