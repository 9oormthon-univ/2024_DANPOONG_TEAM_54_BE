package com.example.paperplane.domain.review.dto;

import com.example.paperplane.domain.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long reviewId,
        String content,
        String username,
        LocalDateTime createdAt
) {
    public ReviewResponse(Review review) {
        this(
                review.getReviewId(),
                review.getContent(),
                review.getUser().getUsername(),
                review.getCreatedAt()
        );
    }
}
