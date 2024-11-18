package com.example.paperplane.domain.auth.controller;

import com.example.paperplane.domain.auth.dto.KakaoUserInfoResponse;
import com.example.paperplane.domain.auth.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "kakao", description = "kakao 로그인 관련 API")
public class KakaoController {

    private final KakaoService kakaoService;
    private final HttpSession httpSession;

    @GetMapping("/kakao/login")
    @Operation(summary = "카카오 로그인")
    public ResponseEntity<String> login(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessToken(code);
        httpSession.setAttribute("token", accessToken);

        KakaoUserInfoResponse userInfo = kakaoService.getUserProfile(accessToken);
        return ResponseEntity.ok("User Info: " + userInfo);
    }

    @Operation(summary = "카카오 로그아웃")
    @GetMapping("/kakao/logout")
    public ResponseEntity<String> logout() {
        String accessToken = (String) httpSession.getAttribute("token");
        return ResponseEntity.ok(kakaoService.logout(accessToken));
    }

    @GetMapping("/kakao/auth")
    @Operation(summary = "카카오 로그인 테스트")
    public ResponseEntity<String> redirectToKakao() {
        String loginUrl = kakaoService.getKakaoLoginUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, loginUrl)
                .build();
    }
}
