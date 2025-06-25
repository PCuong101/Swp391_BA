package org.Scsp.com.repository;

import org.Scsp.com.model.QuitPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface QuitPlanRepository extends JpaRepository<QuitPlan, Long> {

    Optional<QuitPlan> findByUser_UserId(Long userId);
}