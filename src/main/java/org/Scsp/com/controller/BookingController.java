package org.Scsp.com.controller;

import org.Scsp.com.dto.BookingDTO;
import org.Scsp.com.dto.BookingRequest;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.repository.ScheduleRepository;
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
    @Autowired
    private ScheduleRepository scheduleRepo;

    // 🔹 Lấy danh sách lịch còn trống theo coachId và ngày
    @GetMapping("/available")
    public List<ScheduleDTO> getAvailableSchedules(
            @RequestParam Long coachId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return bookingService.getAvailableSchedules(coachId, date);
    }

    @PutMapping("/cancel")
    public String cancelSchedule(
            @RequestParam Long coachId,
            @RequestParam Long scheduleId) {

        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!schedule.getCoach().getUserId().equals(coachId)) {
            return "⛔ Bạn không có quyền hủy lịch này!";
        }

        schedule.setAvailable(false); // Đánh dấu không còn khả dụng
        scheduleRepo.save(schedule);
        return "✅ Lịch đã được hủy thành công.";
    }

    // 🔹 Đặt lịch mới
    @PostMapping("/create")
    public Booking createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest.userId, bookingRequest.scheduleId, bookingRequest.note);
    }

    @PutMapping("/{id}/finish")
    public Booking finishBooking(@PathVariable Long id) {
        return bookingService.finishBooking(id);
    }

    @GetMapping("/get-booking/{userId}")
    public List<BookingDTO> getBookingsByUserId(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }
}
