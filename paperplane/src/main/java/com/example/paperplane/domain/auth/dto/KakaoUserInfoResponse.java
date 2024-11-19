package com.example.paperplane.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfoResponse(
        String id,
        @JsonProperty("connected_at") String connectedAt,
        Properties properties,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    public record Properties(
            @JsonProperty("profile_image") String profileImage,
            @JsonProperty("thumbnail_image") String thumbnailImage
    ) {
    }

    public record KakaoAccount(
            @JsonProperty("profile_image_needs_agreement") boolean profileImageNeedsAgreement,
            Profile profile
    ) {
    }

    public record Profile(
            @JsonProperty("thumbnail_image_url") String thumbnailImageUrl,
            @JsonProperty("profile_image_url") String profileImageUrl,
            @JsonProperty("is_default_image") boolean isDefaultImage
    ) {
    }
}