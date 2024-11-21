package com.example.paperplane.domain.idea.controller;

import com.example.paperplane.domain.idea.dto.IdeaCatalogResponse;
import com.example.paperplane.domain.idea.dto.IdeaDetailResponse;
import com.example.paperplane.domain.idea.dto.IdeaRequest;
import com.example.paperplane.domain.idea.entity.Category;
import com.example.paperplane.domain.idea.entity.Idea;
import com.example.paperplane.domain.idea.service.IdeaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ideas")
@Tag(name = "idea", description = "idea 관련 API")
public class IdeaController {
    private final IdeaService ideaService;

    @GetMapping("/all")
    @Operation(summary = "아이디어 전체 조회")
    public List<IdeaCatalogResponse> getAllIdeas() {
        return ideaService.getAllIdeas();
    }

    @GetMapping("/category")
    @Operation(summary = "카테고리별 아이디어 조회")
    public ResponseEntity<List<IdeaCatalogResponse>> getIdeasByCategory(@RequestParam String category) {
        Category enumCategory = Category.fromDisplayName(category);
        List<IdeaCatalogResponse> ideas = ideaService.getIdeasByCategory(enumCategory);
        return ResponseEntity.ok(ideas);
    }

    @PostMapping("/create")
    @Operation(summary = "아이디어 작성")
    public Idea createIdea(@RequestBody IdeaRequest request, @RequestParam Long userId) {
        System.out.println("Received request: " + request);
        Category category = Category.fromDisplayName(request.categoryDisplayName()); // 변환
        return ideaService.createIdea(request, userId, category);
    }

    @GetMapping("/{id}")
    @Operation(summary = "아이디어 상세 조회")
    public IdeaDetailResponse getIdeaDetail(@PathVariable Long id, @RequestParam Long userId) {

        return ideaService.getIdeaDetail(id, userId);
    }

    @GetMapping("/user/{username}")
    @Operation(summary = "특정 사용자의 아이디어 조회")
    public List<IdeaCatalogResponse> getIdeasByUsername(@PathVariable String username) {
        return ideaService.getIdeasByUsername(username);
    }
}

