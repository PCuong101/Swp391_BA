package org.Scsp.com.service.impl;

import org.Scsp.com.Enum.BookingStatus;
import org.Scsp.com.dto.BookingDTO;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.dto.ScheduleOverviewDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.model.Slot;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.BookingRepository;
import org.Scsp.com.repository.ScheduleRepository;
import org.Scsp.com.repository.SlotRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private ScheduleRepository scheduleRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UsersRepository userRepo;
    @Autowired private SlotRepository slotRepository;


    @Override
    public List<ScheduleDTO> getAvailableSchedules(Long coachId, LocalDate date) {
        List<Schedule> schedules = scheduleRepo.findByCoachUserIdAndDateAndIsAvailableTrueAndIsPublishedTrue(coachId, date);
        return schedules.stream()
                .map(s -> new ScheduleDTO(
                        s.getSchedulesID(),
                        s.getCoach().getName(),
                        s.getSlot().getLabel(),
                        s.getDate(),
                        s.isAvailable()
                ))
                .collect(Collectors.toList());
    }



    @Override
    public Booking createBooking(Long userId, Long scheduleId, String note) {
        User user = userRepo.findById(userId).orElseThrow();
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow();

        if (!schedule.isAvailable()) throw new IllegalStateException("Schedule is not available!");

        // Đánh dấu schedule đã được đặt
        schedule.setAvailable(false);
        scheduleRepo.save(schedule);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setMeetingLink("https://meet.google.com/zaq-euie-aar");
        booking.setStatus(BookingStatus.BOOKED);
        booking.setNotes(note);

        // ✅ Lấy ngày + giờ bắt đầu của Slot
        LocalDate date = schedule.getDate();
        LocalDateTime scheduledTime = LocalDateTime.of(
                date,
                schedule.getSlot().getStartTime()
        );
        booking.setScheduledTime(scheduledTime);

        return bookingRepo.save(booking);
    }

    @Override
    public Booking finishBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow();
        booking.setStatus(BookingStatus.FINISHED);
        return bookingRepo.save(booking);
    }

    private int getStatusPriority(BookingStatus status) {
        return switch (status) {
            case BOOKED -> 0;
            case FINISHED -> 1;
            case CANCELED -> 2;
            case EMPTY -> 3;
            default -> 99;
        };
    }

    @Override
    public List<BookingDTO> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepo.findBookingByUser_UserId(userId);

        return bookings.stream()
                .sorted(Comparator.comparing((Booking b) -> b.getSchedule().getDate()).reversed()
                        .thenComparing(b -> b.getSchedule().getSlot().getStartTime())
                        .thenComparing(b -> getStatusPriority(b.getStatus())
                        ))
                .map(b -> {
                    Slot slot = b.getSchedule().getSlot();
                    return new BookingDTO(
                            b.getSchedule().getDate(),
                            b.getMeetingLink(),
                            b.getNotes(),
                            b.getStatus(),
                            slot.getStartTime(),
                            slot.getEndTime(),
                            b.getSchedule().getCoach().getName(),
                            b.getSchedule().getCoach().getUserId(),
                            b.getSchedule().getSchedulesID(),
                            b.getBookingID()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleOverviewDTO> getCoachScheduleWithBookings(Long coachId) {
        List<Schedule> schedules = scheduleRepo.findByCoachUserId(coachId);

        return schedules.stream().map(s -> {
            ScheduleOverviewDTO dto = new ScheduleOverviewDTO();
            dto.setScheduleId(s.getSchedulesID());
            dto.setDate(s.getDate());
            dto.setSlotLabel(s.getSlot().getLabel());
            dto.setAvailableLabel(s.isAvailable() ? "Còn trống" : "Đã đặt");
            dto.setPublished(s.isPublished());

            if (!s.isAvailable()) {

                // ================== SỬA LỖI 2: LOGIC TÌM BOOKING ==================
                // Ưu tiên tìm booking đang hoạt động (BOOKED).
                Booking booking = bookingRepo.findByScheduleAndStatus(s, BookingStatus.BOOKED)
                        // Nếu không tìm thấy, thử tìm booking đã hoàn thành (FINISHED).
                        .orElse(bookingRepo.findByScheduleAndStatus(s, BookingStatus.FINISHED).orElse(null));

                if (booking != null) {
                    dto.setBookedByName(booking.getUser().getName());
                    dto.setBookedByEmail(booking.getUser().getEmail());
                    dto.setNotes(booking.getNotes());
                    dto.setBookingId(booking.getBookingID());
                    dto.setBookingStatus(booking.getStatus().name());
                    dto.setMeetingLink(booking.getMeetingLink()); // <- thêm dòng này
                } else {
                    // Xử lý trường hợp hiếm: slot đã đặt nhưng không có booking BOOKED hoặc FINISHED
                    // (có thể là CANCELED đã bị xóa logic)
                    dto.setBookingStatus("UNKNOWN");
                }
            } else {
                dto.setNotes("");
                dto.setBookingStatus("EMPTY");
            }

            return dto;
        }).collect(Collectors.toList());
    }

    // =========================================================
    // HÀM 2: TẠO HÀM MỚI CHO COACH
    // =========================================================
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleOverviewDTO> getCoachPublishedSchedule(Long coachId) {
        // Hàm này CHỈ LẤY các lịch đã được PUBLISHED
        List<Schedule> schedules = scheduleRepo.findByCoachUserIdAndIsPublishedTrue(coachId);

        // Logic bên dưới giống hệt hàm trên, chỉ khác nguồn dữ liệu đầu vào
        return schedules.stream().map(s -> {
            ScheduleOverviewDTO dto = new ScheduleOverviewDTO();
            dto.setScheduleId(s.getSchedulesID());
            dto.setDate(s.getDate());
            dto.setSlotLabel(s.getSlot().getLabel());
            dto.setAvailableLabel(s.isAvailable() ? "Còn trống" : "Đã đặt");
            dto.setPublished(s.isPublished());

            if (!s.isAvailable()) {
                Booking booking = bookingRepo.findByScheduleAndStatus(s, BookingStatus.BOOKED)
                        .orElse(bookingRepo.findByScheduleAndStatus(s, BookingStatus.FINISHED).orElse(null));

                if (booking != null) {
                    dto.setBookedByName(booking.getUser().getName());
                    dto.setBookedByEmail(booking.getUser().getEmail());
                    dto.setNotes(booking.getNotes());
                    dto.setBookingId(booking.getBookingID());
                    dto.setBookingStatus(booking.getStatus().name());
                    dto.setMeetingLink(booking.getMeetingLink());
                } else {
                    dto.setBookingStatus("UNKNOWN");
                }
            } else {
                dto.setNotes("");
                dto.setBookingStatus("EMPTY");
            }
            return dto;
        }).collect(Collectors.toList());
    }
}