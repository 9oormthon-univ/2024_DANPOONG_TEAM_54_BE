package com.example.paperplane.domain.comment.controller;

import com.example.paperplane.domain.comment.dto.CommentRequest;
import com.example.paperplane.domain.comment.dto.CommentResponse;
import com.example.paperplane.domain.comment.entity.Comment;
import com.example.paperplane.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ideas/{ideaId}/comments")
@Tag(name = "comment", description = "댓글 관련 API")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "댓글 목록 조회")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long ideaId) {
        List<CommentResponse> comments = commentService.getComments(ideaId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    @Operation(summary = "댓글 작성")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long ideaId,
            @RequestParam Long userId,
            @RequestBody CommentRequest request
    ) {
        Comment comment = commentService.createComment(ideaId, userId, request);
        return ResponseEntity.ok(new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.isAuthor(),
                comment.getUser().getUserId(),
                comment.getUser().getUsername(),
                comment.getCreatedAt(),
                null // No children for a new comment
        ));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
