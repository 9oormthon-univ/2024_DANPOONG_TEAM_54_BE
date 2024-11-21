package com.example.paperplane.domain.comment.service;

import com.example.paperplane.domain.comment.dto.CommentRequest;
import com.example.paperplane.domain.comment.dto.CommentResponse;
import com.example.paperplane.domain.comment.entity.Comment;
import com.example.paperplane.domain.comment.repository.CommentRepository;
import com.example.paperplane.domain.idea.entity.Idea;
import com.example.paperplane.domain.idea.repository.IdeaRepository;
import com.example.paperplane.domain.user.entity.User;
import com.example.paperplane.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> getComments(Long ideaId) {
        List<Comment> comments = commentRepository.findByIdea_IdeaIdAndParentIsNull(ideaId);

        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.isAuthor(),
                        comment.getUser().getUserId(),
                        comment.getUser().getUsername(),
                        comment.getCreatedAt(),
                        comment.getChildren().stream()
                                .map(child -> new CommentResponse(
                                        child.getId(),
                                        child.getContent(),
                                        child.isAuthor(),
                                        child.getUser().getUserId(),
                                        child.getUser().getUsername(),
                                        child.getCreatedAt(),
                                        null
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public Comment createComment(Long ideaId, Long userId, CommentRequest request) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new IllegalArgumentException("Idea not found: ID = " + ideaId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: ID = " + userId));

        Comment parent = null;
        if (request.parentId() != null && request.parentId() > 0) {
            parent = commentRepository.findById(request.parentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found: ID = " + request.parentId()));
        }

        boolean isAuthor = idea.getUser().getUserId().equals(userId);

        Comment comment = new Comment(idea, user, request.content(), parent, isAuthor);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found: ID = " + commentId));
        commentRepository.delete(comment);
    }
}
