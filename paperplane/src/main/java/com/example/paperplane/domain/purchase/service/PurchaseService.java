package com.example.paperplane.domain.purchase.service;

import com.example.paperplane.domain.idea.dto.IdeaCatalogResponse;
import com.example.paperplane.domain.idea.entity.Idea;
import com.example.paperplane.domain.idea.repository.IdeaRepository;
import com.example.paperplane.domain.purchase.entity.Purchase;
import com.example.paperplane.domain.purchase.repository.PurchaseRepository;
import com.example.paperplane.domain.user.entity.User;
import com.example.paperplane.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final UserRepository userRepository;
    private final IdeaRepository ideaRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public Purchase purchaseIdea(Long buyerId, Long ideaId) {

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found: ID = " + buyerId));
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new IllegalArgumentException("Idea not found: ID = " + ideaId));

        User seller = idea.getUser();


        if (buyer.getPoints() < idea.getPrice()) {
            throw new IllegalArgumentException("Not enough points to purchase the idea");
        }


        buyer.setPoints(buyer.getPoints() - idea.getPrice());
        userRepository.save(buyer);


        seller.setPoints(seller.getPoints() + idea.getPrice());
        userRepository.save(seller);


        Purchase purchase = new Purchase(buyer, idea, idea.getPrice());
        return purchaseRepository.save(purchase);
    }

    public List<IdeaCatalogResponse> getPurchasedIdeas(Long userId) {
        return purchaseRepository.findByUser_UserId(userId)
                .stream()
                .map(purchase -> new IdeaCatalogResponse(purchase.getIdea()))
                .collect(Collectors.toList());
    }

    public List<IdeaCatalogResponse> getSoldIdeas(Long userId) {
        return purchaseRepository.findByIdea_User_UserId(userId)
                .stream()
                .map(purchase -> new IdeaCatalogResponse(purchase.getIdea()))
                .collect(Collectors.toList());
    }


}