package org.Scsp.com.repository;

import org.Scsp.com.Enum.BookingStatus; // QUAN TRỌNG: Thêm import này
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByUser_UserId(Long userId);
    List<Booking> findBookingByUser_UserIdOrderByBookingDateDesc(Long userId);

    // Phương thức cũ gây lỗi khi có nhiều booking cho 1 schedule
    Optional<Booking> findBySchedule(Schedule schedule);

    List<Booking> findByScheduledTimeBetween(LocalDateTime start, LocalDateTime end);
    Optional<Booking> findByScheduleAndStatus(Schedule schedule, BookingStatus status);
}