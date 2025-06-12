
package org.Scsp.com.repository;

import org.Scsp.com.model.UserDailyLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserDailyLogsRepository extends JpaRepository<UserDailyLogs, Long> {
    UserDailyLogs findByQuitPlan_PlanIdAndLogDate(Long quitPlanPlanId, LocalDateTime logDate);
}