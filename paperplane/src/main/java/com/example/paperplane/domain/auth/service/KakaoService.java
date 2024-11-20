package com.example.paperplane.domain.auth.service;

import com.example.paperplane.domain.auth.dto.KakaoUserInfoResponse;
import com.example.paperplane.domain.user.entity.User;
import com.example.paperplane.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final WebClient webClient;
    private final UserRepository userRepository;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${kakao.logout-uri}")
    private String logoutUri;

    public String getAccessToken(String code) {
        String response = webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", clientId)
                        .with("redirect_uri", redirectUri)
                        .with("client_secret", clientSecret)
                        .with("code", code))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Access Token Response: {}", response);
        return extractAccessToken(response);
    }

    private String extractAccessToken(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            log.error("Failed to extract access token", e);
            throw new RuntimeException("Access token extraction failed");
        }
    }

    public KakaoUserInfoResponse getUserProfile(String accessToken) {
        String response = webClient.get()
                .uri(userInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Kakao User Profile Response: {}", response);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response, KakaoUserInfoResponse.class);
        } catch (Exception e) {
            log.error("Failed to parse Kakao API response: {}", response, e);
            throw new RuntimeException("Invalid user profile data received from Kakao API");
        }
    }


    public boolean isFirstLogin(KakaoUserInfoResponse userInfo) {
        return userRepository.findByKakaoId(userInfo.id().toString()).isEmpty();
    }

    public void registerNewUser(KakaoUserInfoResponse userInfo) {
        User newUser = new User();
        newUser.setKakaoId(userInfo.id());
        newUser.setUsername("User_" + userInfo.id());
        newUser.setProfileImage(userInfo.kakaoAccount().profile().profileImageUrl());
        userRepository.save(newUser);
    }


    public String logout(String accessToken) {
        return webClient.post()
                .uri(logoutUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getKakaoLoginUrl() {
        return "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";
    }

    public Long getUserId(KakaoUserInfoResponse userInfo) {
        return userRepository.findByKakaoId(userInfo.id().toString())
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
