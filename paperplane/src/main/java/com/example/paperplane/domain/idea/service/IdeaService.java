package com.example.paperplane.domain.idea.service;

import com.example.paperplane.domain.idea.dto.IdeaCatalogResponse;
import com.example.paperplane.domain.idea.dto.IdeaDetailResponse;
import com.example.paperplane.domain.idea.dto.IdeaRequest;
import com.example.paperplane.domain.idea.entity.Category;
import com.example.paperplane.domain.idea.entity.Idea;
import com.example.paperplane.domain.idea.repository.IdeaRepository;
import com.example.paperplane.domain.user.entity.User;
import com.example.paperplane.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdeaService {
    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;

    public List<IdeaCatalogResponse> getIdeasByCategory(Category category) {
        return ideaRepository.findByCategory(category)
                .stream()
                .map(IdeaCatalogResponse::new)
                .collect(Collectors.toList());
    }

    public List<IdeaCatalogResponse> getAllIdeas() {
        return ideaRepository.findAll().stream()
                .map(IdeaCatalogResponse::new)
                .collect(Collectors.toList());
    }

    public Idea createIdea(IdeaRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Idea idea = new Idea(
                user,
                request.title(),
                request.category(),
                request.description(),
                request.tags(),
                request.price()
        );
        return ideaRepository.save(idea);
    }

    @Transactional(readOnly = true)
    public IdeaDetailResponse getIdeaDetail(Long id) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디어를 찾을 수 없습니다: ID = " + id));

        User user = idea.getUser();
        return new IdeaDetailResponse(
                idea.getIdeaId(),
                idea.getTitle(),
                idea.getCategory(),
                idea.getDescription(),
                idea.getTags(),
                idea.getPrice(),
                user.getUsername(),
                idea.getCreatedAt()
        );
    }
}

