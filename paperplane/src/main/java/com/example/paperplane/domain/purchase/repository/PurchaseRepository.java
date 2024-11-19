package com.example.paperplane.domain.purchase.repository;

import com.example.paperplane.domain.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    boolean existsByUser_UserIdAndIdea_IdeaId(Long userId, Long ideaId);
}
