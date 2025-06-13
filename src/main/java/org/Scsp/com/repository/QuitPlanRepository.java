package org.Scsp.com.repository;

import org.Scsp.com.model.QuitPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface QuitPlanRepository extends JpaRepository<QuitPlan, Long> {
    Optional<QuitPlan> findLatestByUser_UserID(Long userUserID);

    Optional<QuitPlan> findByUser_UserID(Long userId);
}