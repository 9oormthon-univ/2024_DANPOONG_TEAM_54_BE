package com.example.paperplane.domain.idea.repository;

import com.example.paperplane.domain.idea.entity.Category;
import com.example.paperplane.domain.idea.entity.Idea;
import com.example.paperplane.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByCategory(Category category);
    List<Idea> findByUser(User user);
    @Query("SELECT i FROM Idea i WHERE i.title LIKE %:keyword% OR i.tags LIKE %:keyword%")
    List<Idea> searchByKeyword(@Param("keyword") String keyword);

}

