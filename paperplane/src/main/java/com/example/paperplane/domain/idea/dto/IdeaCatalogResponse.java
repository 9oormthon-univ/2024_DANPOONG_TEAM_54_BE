package com.example.paperplane.domain.idea.dto;

import com.example.paperplane.domain.idea.entity.Category;
import com.example.paperplane.domain.idea.entity.Idea;

import java.time.LocalDateTime;

public record IdeaCatalogResponse(
        Long ideaId,
        String title,
        Category category,
        LocalDateTime createdAt,
        String tags,
        int price
) {
    public IdeaCatalogResponse(Idea idea) {
        this(
                idea.getIdeaId(),
                idea.getTitle(),
                idea.getCategory(),
                idea.getCreatedAt(),
                idea.getTags(),
                idea.getPrice()
        );
    }
}



