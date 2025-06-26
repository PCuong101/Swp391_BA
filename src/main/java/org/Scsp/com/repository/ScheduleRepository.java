package org.Scsp.com.repository;

import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByCoachUserIdAndSlotSlotIDAndDate(Long coachId, Long slotId, LocalDate date);
    List<Schedule> findByCoachUserIdAndDateAndIsAvailableTrue(Long coachId, LocalDate date);
    List<Schedule> findByCoach_UserIdAndDateAfterAndDateBefore(Long coachId, LocalDate date, LocalDate date2);

    List<Schedule> findByCoachUserId(Long coachId);



}
