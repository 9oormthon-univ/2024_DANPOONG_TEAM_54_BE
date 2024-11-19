package com.example.paperplane.domain.purchase.controller;

import com.example.paperplane.domain.purchase.entity.Purchase;
import com.example.paperplane.domain.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
