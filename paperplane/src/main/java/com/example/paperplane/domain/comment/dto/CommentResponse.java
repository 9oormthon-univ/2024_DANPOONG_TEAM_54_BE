package com.example.paperplane.domain.comment.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
        Long id,
        String content,
        boolean isAuthor,
        Long userId,
        String username,
        LocalDateTime createdAt,
        List<CommentResponse> children
) {}
