package org.Scsp.com.repository;

import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByCoachUserIdAndSlotSlotIDAndDate(Long coachId, Long slotId, LocalDate date);

    List<Schedule> findByCoachUserIdAndDateAfterAndDateBeforeAndIsPublishedTrue(Long coachId, LocalDate after, LocalDate before);

    List<Schedule> findByCoachUserId(Long coachId);
    List<Schedule> findByCoach_UserIdAndDateAfterAndDateBeforeAndIsPublishedTrue(
            Long coachId,
            LocalDate start,
            LocalDate end
    );
    List<Schedule> findByCoachUserIdAndDateAndIsAvailableTrueAndIsPublishedTrue(Long coachId, LocalDate date);
    List<Schedule> findByCoachUserIdAndIsPublishedTrue(Long coachId);
}





