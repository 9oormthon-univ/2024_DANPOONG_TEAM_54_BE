package com.example.paperplane.domain.idea.dto;

import com.example.paperplane.domain.idea.entity.Category;
import com.example.paperplane.domain.idea.entity.Idea;

import java.time.LocalDateTime;

public record IdeaDetailResponse(
        Long ideaId,
        String title,
        Category category,
        String description,
        String tags,
        int price,
        String author,
        LocalDateTime createdAt,
        String status

) {
    public IdeaDetailResponse(Idea idea, String status) {
        this(
                idea.getIdeaId(),
                idea.getTitle(),
                idea.getCategory(),
                idea.getDescription(),
                idea.getTags(),
                idea.getPrice(),
                idea.getUser().getUsername(),
                idea.getCreatedAt(),
                status
        );
    }

}
