package org.Scsp.com.repository;

import org.Scsp.com.dto.BookingDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByUser_UserId(Long userId);
    List<Booking> findBookingByUser_UserIdOrderByBookingDateDesc(Long userId);
    Optional<Booking> findBySchedule(Schedule schedule);
}
