package com.example.paperplane.domain.review.controller;

import com.example.paperplane.domain.review.dto.ReviewResponse;
import com.example.paperplane.domain.review.entity.Review;
import com.example.paperplane.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "review", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping
    @Operation(summary = "리뷰 작성")
    public ReviewResponse addReview(
            @RequestParam Long ideaId,
            @RequestParam Long userId,
            @RequestParam String content) {
        Review review = reviewService.addReview(ideaId, userId, content);
        return new ReviewResponse(review);
    }


    @GetMapping("/{ideaId}")
    @Operation(summary = "리뷰 조회")
    public List<ReviewResponse> getReviewsForIdea(@PathVariable Long ideaId) {
        return reviewService.getReviewsForIdea(ideaId).stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }
}
