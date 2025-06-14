package org.Scsp.com.service;

import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    public List<ScheduleDTO> getAvailableSchedules(Long coachId, LocalDate date);
    Booking createBooking(Long userId, Long scheduleId);
}
