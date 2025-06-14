package org.Scsp.com.service;

import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<Schedule> getAvailableSchedules(Long coachId, LocalDate date);
    Booking createBooking(Long userId, Long scheduleId);
}
