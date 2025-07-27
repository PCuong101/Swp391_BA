package org.Scsp.com.controller;

import org.Scsp.com.Enum.BookingStatus;
import org.Scsp.com.dto.BookingDTO;
import org.Scsp.com.dto.BookingRequest;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.dto.ScheduleOverviewDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.repository.BookingRepository;
import org.Scsp.com.repository.ScheduleRepository;
import org.Scsp.com.service.BookingService;
import org.Scsp.com.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private BookingRepository bookingRepo;
    
    @Autowired
    private NotificationService notificationService;

    // 🔹 Lấy danh sách lịch còn trống theo coachId và ngày
    @GetMapping("/available")
    public List<ScheduleDTO> getAvailableSchedules(
            @RequestParam Long coachId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return bookingService.getAvailableSchedules(coachId, date);
    }

    @PutMapping("/coach/cancel")
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
    @PutMapping("/cancel")
    public String cancelBooking(@RequestParam long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELED) {
            return "⛔ Lịch đã được hủy trước đó.";
        }
        
        // Lưu thông tin trước khi hủy để gửi thông báo
        String coachName = booking.getSchedule().getCoach().getName();
        String timeSlot = booking.getSchedule().getSlot().getLabel();
        String bookingDate = booking.getSchedule().getDate().toString();
        
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepo.save(booking);
        Schedule schedule = booking.getSchedule();
        schedule.setAvailable(true);
        scheduleRepo.save(schedule);

        // 🔔 Gửi thông báo hủy lịch
        notificationService.createNotification(
                booking.getUser(),
                "❌ Lịch hẹn đã được hủy",
                String.format("Lịch hẹn với chuyên gia %s vào %s (%s) đã được hủy thành công. " +
                        "Bạn có thể đặt lịch hẹn mới bất cứ lúc nào.",
                        coachName, timeSlot, bookingDate)
        );

        return ("✅ Lịch đã được hủy và slot được mở lại.");
    }

    // 🔹 Đặt lịch mới
    @PostMapping("/create")
    public Booking createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest.userId, bookingRequest.scheduleId, bookingRequest.note);
    }
    @GetMapping("/coach/{coachId}/schedule")
    public ResponseEntity<List<ScheduleOverviewDTO>> getCoachSchedule(@PathVariable Long coachId) {
        List<ScheduleOverviewDTO> result = bookingService.getCoachScheduleWithBookings(coachId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/coach/{coachId}/published-schedule")
    public ResponseEntity<List<ScheduleOverviewDTO>> getCoachPublishedSchedule(@PathVariable Long coachId) {
        List<ScheduleOverviewDTO> result = bookingService.getCoachPublishedSchedule(coachId);
        return ResponseEntity.ok(result);
    }//

    @PutMapping("/{id}/finish")
    public Booking finishBooking(@PathVariable Long id) {
        return bookingService.finishBooking(id);
    }

    @GetMapping("/get-booking/{userId}")
    public List<BookingDTO> getBookingsByUserId(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }
}
