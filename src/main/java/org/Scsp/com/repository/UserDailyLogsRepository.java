
package org.Scsp.com.repository;

import org.Scsp.com.model.UserDailyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface UserDailyLogsRepository extends JpaRepository<UserDailyLog, Long> {
    UserDailyLog findByQuitPlan_PlanIDAndLogDateBetween(Long quitPlanPlanID, LocalDateTime startOfDay, LocalDateTime endOfDay);
    List<UserDailyLog> findByQuitPlan_PlanIDOrderByLogDateAsc(Long planID);
    List<UserDailyLog> findByQuitPlan_PlanID(Long planID);
    Optional<UserDailyLog> findByLogDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT u FROM UserDailyLog u WHERE u.quitPlan.planID = :quitPlanId AND u.logDate BETWEEN :startOfDay AND :endOfDay")
    List<UserDailyLog> findRecentLogs( Long quitPlanId, LocalDateTime startOfDay, LocalDateTime endOfDay);
    Integer countByQuitPlan_PlanID(Long quitPlanPlanID);
}