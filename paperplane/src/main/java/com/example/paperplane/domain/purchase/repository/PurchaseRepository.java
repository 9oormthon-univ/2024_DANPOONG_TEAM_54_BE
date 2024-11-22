package com.example.paperplane.domain.purchase.repository;

import com.example.paperplane.domain.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    boolean existsByUser_UserIdAndIdea_IdeaId(Long userId, Long ideaId);
    List<Purchase> findByUser_UserId(Long userId);
    List<Purchase> findByIdea_User_UserId(Long userId);
}
