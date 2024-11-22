package com.example.paperplane.domain.idea.dto;

import com.example.paperplane.domain.idea.entity.Category;
import org.springframework.web.multipart.MultipartFile;

public record IdeaRequest(
        String title,
        String categoryDisplayName,
        String description,
        String tags,
        int price,
        MultipartFile file
) {
}
