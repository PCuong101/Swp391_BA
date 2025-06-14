package org.Scsp.com.controller;

import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
 // Cho phép frontend React truy cập nếu khác domain
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // 🔹 Lấy danh sách lịch còn trống theo coachId và ngày
    @GetMapping("/available")
    public List<ScheduleDTO> getAvailableSchedules(
            @RequestParam Long coachId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return bookingService.getAvailableSchedules(coachId, date);
    }

    // 🔹 Đặt lịch mới
    @PostMapping("/create")
    public Booking createBooking(
            @RequestParam Long userId,
            @RequestParam Long scheduleId) {
        return bookingService.createBooking(userId, scheduleId);
    }
}
