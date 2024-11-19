package com.example.paperplane.domain.idea.dto;

import com.example.paperplane.domain.idea.entity.Category;

import java.time.LocalDateTime;

public record IdeaDetailResponse(
        Long id,
        String title,
        Category category,
        String description,
        String tags,
        int price,
        String author,
        LocalDateTime createdAt,
        String status
) {
}
