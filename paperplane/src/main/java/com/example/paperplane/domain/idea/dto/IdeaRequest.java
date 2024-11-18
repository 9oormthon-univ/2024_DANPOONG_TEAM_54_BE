package com.example.paperplane.domain.idea.dto;

import com.example.paperplane.domain.idea.entity.Category;

public record IdeaRequest(
        String title,
        Category category,
        String description,
        String tags,
        int price
) {
}
