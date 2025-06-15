
package org.Scsp.com.repository;

import org.Scsp.com.model.UserDailyLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface UserDailyLogsRepository extends JpaRepository<UserDailyLog, Long> {
    UserDailyLog findByQuitPlan_PlanIDAndLogDateBetween(Long quitPlanPlanID, LocalDateTime startOfDay, LocalDateTime endOfDay);
    List<UserDailyLog> findByQuitPlan_PlanID(Long planID);
}