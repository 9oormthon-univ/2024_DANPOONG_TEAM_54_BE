package com.example.paperplane.domain.review.service;

import com.example.paperplane.domain.idea.entity.Idea;
import com.example.paperplane.domain.idea.repository.IdeaRepository;
import com.example.paperplane.domain.review.entity.Review;
import com.example.paperplane.domain.review.repository.ReviewRepository;
import com.example.paperplane.domain.user.entity.User;
import com.example.paperplane.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;

    @Transactional
    public Review addReview(Long ideaId, Long userId, String content) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new IllegalArgumentException("Idea not found: ID = " + ideaId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: ID = " + userId));

        Review review = new Review(idea, user, content);
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsForIdea(Long ideaId) {
        return reviewRepository.findByIdea_IdeaId(ideaId);
    }
}
