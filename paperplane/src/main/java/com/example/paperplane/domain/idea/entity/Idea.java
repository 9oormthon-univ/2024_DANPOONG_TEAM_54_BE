package com.example.paperplane.domain.idea.entity;

import com.example.paperplane.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ideas")
@NoArgsConstructor
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idea_id")
    private Long ideaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    @Column
    private String tags;

    @Column
    private int views = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Idea(User user, String title, Category category, String description, String tags, int price) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.description = description;
        this.tags = tags;
        this.price = price;
    }

//    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Favorite> favorites;
//
//    // 좋아요 수 계산
//    public int getFavoriteCount() {
//        return favorites.size();
//    }
}

