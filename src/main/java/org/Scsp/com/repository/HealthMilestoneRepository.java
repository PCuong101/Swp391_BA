package org.Scsp.com.repository;

import org.Scsp.com.model.HealthMilestone;
import org.Scsp.com.model.QuitPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HealthMilestoneRepository extends JpaRepository<HealthMilestone, Long> {
    List<HealthMilestone> findByQuitPlan(QuitPlan quitPlan);
    List<HealthMilestone> findByQuitPlan_PlanId(Long quitPlanPlanId);
    List<HealthMilestone> findByAchievedFalseAndExpectedDateBefore(LocalDateTime now);
}