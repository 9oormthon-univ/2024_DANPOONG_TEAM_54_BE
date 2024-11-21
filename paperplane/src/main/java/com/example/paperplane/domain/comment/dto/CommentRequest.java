package com.example.paperplane.domain.comment.dto;

public record CommentRequest(
        String content,
        Long parentId
) {}
