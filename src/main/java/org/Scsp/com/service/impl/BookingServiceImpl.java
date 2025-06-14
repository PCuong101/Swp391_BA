package org.Scsp.com.service.impl;

import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.BookingRepository;
import org.Scsp.com.repository.ScheduleRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private ScheduleRepository scheduleRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UsersRepository userRepo;

    @Override
    public List<Schedule> getAvailableSchedules(Long coachId, LocalDate date) {
        return scheduleRepo.findByCoachUserIDAndDateAndIsAvailableTrue(coachId, date);
    }

    @Override
    public Booking createBooking(Long userId, Long scheduleId) {
        User user = userRepo.findById(userId).orElseThrow();
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow();

        if (!schedule.isAvailable()) throw new IllegalStateException("Schedule is not available!");

        // Đánh dấu schedule đã được đặt
        schedule.setAvailable(false);
        scheduleRepo.save(schedule);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setStatus("CONFIRMED");
        return bookingRepo.save(booking);
    }
}
