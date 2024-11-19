package com.example.paperplane.domain.purchase.entity;

import com.example.paperplane.domain.idea.entity.Idea;
import com.example.paperplane.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 산 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDateTime purchaseTime = LocalDateTime.now();

    public Purchase(User user, Idea idea, int price) {
        this.user = user;
        this.idea = idea;
        this.price = price;
    }
}
