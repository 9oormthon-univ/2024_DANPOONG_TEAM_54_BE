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

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Tag(name = "kakao", description = "kakao 로그인 관련 API")
public class KakaoController {

    private final KakaoService kakaoService;
    private final HttpSession httpSession;

    @GetMapping("/kakao/login")
    @Operation(summary = "카카오 로그인")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("code") String code) {
        // Access Token 요청 및 저장
        String accessToken = kakaoService.getAccessToken(code);
        httpSession.setAttribute("token", accessToken);

        // 사용자 정보 요청
        KakaoUserInfoResponse userInfo = kakaoService.getUserProfile(accessToken);

        // 첫 로그인 여부 확인
        boolean isFirstLogin = kakaoService.isFirstLogin(userInfo);
        if (isFirstLogin) {
            kakaoService.registerNewUser(userInfo);
        }

        Long userId = kakaoService.getUserId(userInfo);

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("isFirstLogin", isFirstLogin);
        response.put("userInfo", userInfo);
        response.put("userId", userId);

        return ResponseEntity.ok(response);
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
