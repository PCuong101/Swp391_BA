package org.Scsp.com.service;

import org.Scsp.com.dto.BookingDTO;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.dto.ScheduleOverviewDTO;
import org.Scsp.com.model.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    public List<ScheduleDTO> getAvailableSchedules(Long coachId, LocalDate date);
    Booking createBooking(Long userId, Long scheduleId, String note);

    Booking finishBooking(Long bookingId);
    public List<ScheduleOverviewDTO> getCoachScheduleWithBookings(Long coachId);
    List<BookingDTO> getBookingsByUserId(Long userId);
}
