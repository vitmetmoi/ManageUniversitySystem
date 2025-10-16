package com.example.myapp.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.auth.dto.LoginRequest;
import com.example.myapp.auth.dto.LoginResponse;
import com.example.myapp.auth.security.CookieUtil;
import com.example.myapp.auth.security.JwtTokenProvider;
import com.example.myapp.auth.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse resp = loginService.loginService(request);
        String access = tokenProvider.generateAccessToken(request.getEmail());
        String refresh = tokenProvider.generateRefreshToken(request.getEmail());

        boolean secure = false; // local dev over HTTP; set true when using HTTPS
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, CookieUtil.accessCookie(access, secure).toString())
                .header(HttpHeaders.SET_COOKIE, CookieUtil.refreshCookie(refresh, secure).toString())
                .body(resp);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request) {
        String refresh = null;
        if (request.getCookies() != null) {
            for (var c : request.getCookies()) if ("REFRESH_TOKEN".equals(c.getName())) refresh = c.getValue();
        }
        if (refresh != null && tokenProvider.validateToken(refresh)) {
            String subject = tokenProvider.getSubject(refresh);
            String newAccess = tokenProvider.generateAccessToken(subject);
            boolean secure = false; // local dev over HTTP; set true when using HTTPS
            return ResponseEntity.noContent()
                    .header(HttpHeaders.SET_COOKIE, CookieUtil.accessCookie(newAccess, secure).toString())
                    .build();
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        boolean secure = false; // local dev over HTTP; set true when using HTTPS
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, CookieUtil.clearCookie("ACCESS_TOKEN", secure).toString())
                .header(HttpHeaders.SET_COOKIE, CookieUtil.clearCookie("REFRESH_TOKEN", secure).toString())
                .build();
    }
}


