package com.example.paperplane.domain.comment.repository;

import com.example.paperplane.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIdea_IdeaIdAndParentIsNull(Long ideaId);
}
