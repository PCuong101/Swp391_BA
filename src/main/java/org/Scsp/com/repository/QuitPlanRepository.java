package org.Scsp.com.repository;

import org.Scsp.com.model.QuitPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface QuitPlanRepository extends JpaRepository<QuitPlan, Long> {

    Optional<QuitPlan> findByUser_UserId(Long userId);
    // In QuitPlanRepository.java
    @Query("SELECT q FROM QuitPlan q LEFT JOIN FETCH q.milestones WHERE q.user.userId = :userId")
    Optional<QuitPlan> findByUser_UserIdWithMilestones(@Param("userId") Long userId);
}