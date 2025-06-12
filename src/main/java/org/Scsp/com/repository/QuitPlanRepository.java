package org.Scsp.com.repository;

import org.Scsp.com.model.QuitPlans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface QuitPlanRepository extends JpaRepository<QuitPlans, Long> {
    Optional<QuitPlans> findLatestByUser_UserId(Long userUserId);
}