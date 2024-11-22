package com.example.paperplane.domain.purchase.controller;

import com.example.paperplane.domain.idea.dto.IdeaCatalogResponse;
import com.example.paperplane.domain.purchase.entity.Purchase;
import com.example.paperplane.domain.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
@Tag(name = "purchase", description = "아이디어 구매 API")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/{buyerId}/{ideaId}")
    @Operation(summary = "아이디어 구매")
    public Purchase purchaseIdea(@PathVariable Long buyerId, @PathVariable Long ideaId) {
        return purchaseService.purchaseIdea(buyerId, ideaId);
    }

    @GetMapping("/purchases")
    @Operation(summary = "구매한 아이디어 조회")
    public List<IdeaCatalogResponse> getPurchasedIdeas(@RequestParam Long userId) {
        return purchaseService.getPurchasedIdeas(userId);
    }

    @GetMapping("/sales")
    @Operation(summary = "판매한 아이디어 조회")
    public List<IdeaCatalogResponse> getSoldIdeas(@RequestParam Long userId) {
        return purchaseService.getSoldIdeas(userId);
    }
}
