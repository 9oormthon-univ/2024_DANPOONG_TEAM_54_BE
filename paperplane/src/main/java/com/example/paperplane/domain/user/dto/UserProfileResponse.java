package com.example.paperplane.domain.user.dto;

public record UserProfileResponse(
        String username,
        String profileImage,
        int points
) {}
