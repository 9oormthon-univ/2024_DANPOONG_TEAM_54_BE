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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    @Operation(summary = "카테고리별 아이디어 조회", description = "카테고리 한글로 입력하면 됩니다.")
    public ResponseEntity<List<IdeaCatalogResponse>> getIdeasByCategory(@RequestParam String category) {
        Category enumCategory = Category.fromDisplayName(category);
        List<IdeaCatalogResponse> ideas = ideaService.getIdeasByCategory(enumCategory);
        return ResponseEntity.ok(ideas);
    }

//    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "아이디어 작성")
//    public ResponseEntity<IdeaDetailResponse> createIdea(
//            @ModelAttribute IdeaRequest request,
//            @RequestParam Long userId
//    ) {
//        Category category = Category.fromDisplayName(request.categoryDisplayName());
//        Idea savedIdea = ideaService.createIdea(request, userId, request.file(), category);
//        return ResponseEntity.ok(new IdeaDetailResponse(savedIdea, "OWN"));
//    }

//    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "아이디어 작성")
//    public ResponseEntity<IdeaDetailResponse> createIdea(
//            @RequestPart(value = "file", required = false) MultipartFile file, // file은 nullable
//            @RequestPart("title") String title,
//            @RequestPart("categoryDisplayName") String categoryDisplayName,
//            @RequestPart("description") String description,
//            @RequestPart("tags") String tags,
//            @RequestPart("price") Integer price,
//            @RequestParam Long userId
//    ) {
//        Category category = Category.fromDisplayName(categoryDisplayName);
//        IdeaRequest request = new IdeaRequest(title, categoryDisplayName, description, tags, price, file);
//
//        Idea savedIdea = ideaService.createIdea(request, userId, file, category);
//
//        return ResponseEntity.ok(new IdeaDetailResponse(savedIdea, "OWN"));
//    }


//    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "아이디어 작성")
//    public ResponseEntity<IdeaDetailResponse> createIdea(
//            MultipartHttpServletRequest request,
//            @RequestParam Long userId
//    ) {
//        // JSON 데이터 추출
//        String title = request.getParameter("title");
//        String categoryDisplayName = request.getParameter("categoryDisplayName");
//        String description = request.getParameter("description");
//        String tags = request.getParameter("tags");
//        Integer price = Integer.parseInt(request.getParameter("price")); // Ensure this is present in the request
//
//        // Category 변환
//        Category category = Category.fromDisplayName(categoryDisplayName);
//
//        // 파일 처리
//        MultipartFile file = request.getFile("file");
//
//        // IdeaRequest와 동일한 데이터 생성
//        IdeaRequest ideaRequest = new IdeaRequest(title, categoryDisplayName, description, tags, price, file);
//
//        // 서비스 호출
//        Idea savedIdea = ideaService.createIdea(ideaRequest, userId, file, category);
//
//        return ResponseEntity.ok(new IdeaDetailResponse(savedIdea, "OWN"));
//    }


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "아이디어 작성")
    public ResponseEntity<IdeaDetailResponse> createIdea(
            @RequestPart("request") IdeaRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestParam Long userId
    ) {
        Category category = Category.fromDisplayName(request.categoryDisplayName());
        Idea savedIdea = ideaService.createIdea(request, userId, file, category);
        return ResponseEntity.ok(new IdeaDetailResponse(savedIdea, "OWN"));
    }



    @GetMapping("/{id}")
    @Operation(summary = "아이디어 상세 조회", description = "OWN - 본인 아이디어  PURCHASED - 이미 구매한 아이디어  NOT_PURCHASED - 구매하지 않은 아이디어")
    public IdeaDetailResponse getIdeaDetail(@PathVariable Long id, @RequestParam Long userId) {

        return ideaService.getIdeaDetail(id, userId);
    }

    @GetMapping("/user/{username}")
    @Operation(summary = "특정 사용자의 아이디어 조회")
    public List<IdeaCatalogResponse> getIdeasByUsername(@PathVariable String username) {
        return ideaService.getIdeasByUsername(username);
    }

    @GetMapping("/search")
    @Operation(summary = "아이디어 검색", description = "키워드로 아이디어를 검색합니다 (제목 또는 태그 기준).")
    public List<IdeaCatalogResponse> searchIdeas(@RequestParam String keyword) {
        return ideaService.searchIdeas(keyword);
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "아이디어 파일 다운로드", description = "파일 다운로드 링크로 주어집니다.")
    public ResponseEntity<String> downloadFile(@PathVariable Long id, @RequestParam Long userId) {
        String file = ideaService.getIdeaFile(id, userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"")
                .body(file);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "아이디어 삭제")
    public ResponseEntity<Long> deleteIdea(@PathVariable Long id, @RequestParam Long userId) {
        ideaService.deleteIdea(id, userId);
        return ResponseEntity.ok(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "아이디어 수정")
    public void updateIdea(@PathVariable Long id, @RequestParam Long userId, @ModelAttribute IdeaRequest request) {
        Category category = Category.fromDisplayName(request.categoryDisplayName());
        ideaService.updateIdea(id, userId, request);
    }
}

