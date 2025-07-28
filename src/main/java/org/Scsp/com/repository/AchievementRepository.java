package org.Scsp.com.repository;

import org.Scsp.com.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// src/main/java/org/Scsp/com/repository/AchievementRepository.java
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findByUser_UserId(Long userId);

    List<Achievement> findByAchievementTemplate_TemplateID(Long id);
}