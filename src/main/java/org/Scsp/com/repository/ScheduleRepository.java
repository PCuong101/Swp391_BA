package org.Scsp.com.repository;

import org.Scsp.com.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByCoachUserIDAndSlotSlotIDAndDate(Long coachId, Long slotId, LocalDate date);
    List<Schedule> findByCoachUserIDAndDateAndIsAvailableTrue(Long coachId, LocalDate date);
}
