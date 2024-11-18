package com.example.paperplane.domain.auth.dto;

public record KakaoUserInfoResponse(
        String id,
        KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            Profile profile,
            String email
    ) {
    }

    public record Profile(
            String nickname,
            String profileImageUrl
    ) {
    }
}
