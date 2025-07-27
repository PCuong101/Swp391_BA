package org.Scsp.com.scheduler;

import org.Scsp.com.model.Booking;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.BookingRepository;
import org.Scsp.com.repository.TaskCompletionRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Component
public class ReminderScheduler {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private TaskCompletionRepository taskCompletionRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 8 * * *") // Mỗi ngày lúc 8h sáng
    public void sendDailyTaskReminders() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<User> users = userRepository.findAll();

        for (User user : users) {
            // Kiểm tra xem có nhiệm vụ nào chưa hoàn thành trong ngày không
            boolean hasIncomplete = taskCompletionRepository.existsByUserAndCompletedAtBetween(user, startOfDay, endOfDay);

            if (hasIncomplete) {
                notificationService.createNotification(
                        user,
                        "Nhắc nhở nhiệm vụ",
                        "Bạn còn nhiệm vụ chưa hoàn thành hôm nay!"
                );
            }
        }
    }


    @Scheduled(cron = "0 0 7 * * *") // Mỗi ngày 7h sáng
    public void sendRandomMotivation() {
        List<String> messages = List.of(
                "Mỗi ngày không hút thuốc là một chiến thắng.",
                "Bạn đang làm rất tốt. Tiếp tục nhé!",
                "Hãy nhớ lý do bạn bắt đầu hành trình này.",
                "Bạn xứng đáng có một cuộc sống khỏe mạnh không khói thuốc."
        );

        Random random = new Random();

        List<User> users = userRepository.findAll();

        for (User user : users) {
            String message = messages.get(random.nextInt(messages.size()));
            notificationService.createNotification(
                    user,
                    "Thông điệp động viên hôm nay",
                    message
            );
        }
    }



    @Scheduled(cron = "0 5 8 * * *") // Mỗi ngày lúc 8h05 sáng
    public void checkMilestones() {
        LocalDate today = LocalDate.now();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.getQuitPlans() != null && user.getQuitPlans().getStartDate() != null) {
                LocalDate startDate = user.getQuitPlans().getStartDate().toLocalDate();
                long daysNoSmoke = ChronoUnit.DAYS.between(startDate, today);

                if (daysNoSmoke == 7 || daysNoSmoke == 30) {
                    notificationService.createNotification(
                            user,
                            "Chúc mừng!",
                            "Bạn đã không hút thuốc " + daysNoSmoke + " ngày. Tiếp tục cố gắng!"
                    );
                }
            }
        }
    }


    // Nhắc lịch hẹn trước 1 ngày (chạy lúc 9h sáng mỗi ngày)
    @Scheduled(cron = "0 0 9 * * *")
    public void remindBookingsOneDayBefore() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        LocalDateTime dayAfterTomorrow = now.plusDays(2);

        List<Booking> tomorrowBookings = bookingRepository.findByScheduledTimeBetween(tomorrow.toLocalDate().atStartOfDay(), dayAfterTomorrow.toLocalDate().atStartOfDay());

        for (Booking booking : tomorrowBookings) {
            if (booking.getStatus().name().equals("BOOKED")) {
                String coachName = booking.getSchedule().getCoach().getName();
                String timeSlot = booking.getSchedule().getSlot().getLabel();
                String meetingDate = booking.getSchedule().getDate().toString();
                
                notificationService.createNotification(
                        booking.getUser(),
                        "🔔 Nhắc nhở lịch hẹn với chuyên gia",
                        String.format("Bạn có lịch hẹn với chuyên gia %s vào ngày mai (%s) trong khung giờ %s. " +
                                "Hãy chuẩn bị sẵn sàng và tham gia đúng giờ nhé! " +
                                "Link tham gia: %s", 
                                coachName, meetingDate, timeSlot, 
                                booking.getMeetingLink() != null ? booking.getMeetingLink() : "Sẽ được cung cấp sau")
                );
            }
        }
    }

    // Nhắc lịch hẹn trước 1 giờ (chạy mỗi 15 phút)
    @Scheduled(cron = "0 */15 * * * *")
    public void remindBookingsOneHourBefore() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inOneHour = now.plusHours(1);
        LocalDateTime inOneHourFifteen = now.plusHours(1).plusMinutes(15);

        List<Booking> upcomingBookings = bookingRepository.findByScheduledTimeBetween(inOneHour, inOneHourFifteen);

        for (Booking booking : upcomingBookings) {
            if (booking.getStatus().name().equals("BOOKED")) {
                String coachName = booking.getSchedule().getCoach().getName();
                String timeSlot = booking.getSchedule().getSlot().getLabel();
                
                notificationService.createNotification(
                        booking.getUser(),
                        "⏰ Lịch hẹn sắp diễn ra trong 1 giờ",
                        String.format("Buổi tư vấn với chuyên gia %s sẽ bắt đầu trong 1 giờ (%s). " +
                                "Vui lòng chuẩn bị thiết bị và tham gia đúng giờ. " +
                                "Link tham gia: %s", 
                                coachName, timeSlot, 
                                booking.getMeetingLink() != null ? booking.getMeetingLink() : "Đang cập nhật")
                );
            }
        }
    }

    // Nhắc lịch hẹn trước 15 phút (chạy mỗi 5 phút)
    @Scheduled(cron = "0 */5 * * * *")
    public void remindBookingsFifteenMinutesBefore() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inFifteenMinutes = now.plusMinutes(15);
        LocalDateTime inTwentyMinutes = now.plusMinutes(20);

        List<Booking> soonBookings = bookingRepository.findByScheduledTimeBetween(inFifteenMinutes, inTwentyMinutes);

        for (Booking booking : soonBookings) {
            if (booking.getStatus().name().equals("BOOKED")) {
                String coachName = booking.getSchedule().getCoach().getName();
                
                notificationService.createNotification(
                        booking.getUser(),
                        "🚨 Lịch hẹn sắp bắt đầu",
                        String.format("Buổi tư vấn với chuyên gia %s sẽ bắt đầu trong 15 phút. " +
                                "Hãy tham gia ngay bây giờ: %s", 
                                coachName, 
                                booking.getMeetingLink() != null ? booking.getMeetingLink() : "Link sẽ được cập nhật sớm")
                );
            }
        }
    }

    // Nhắc nhở chuyên gia về lịch hẹn sắp tới (chạy mỗi 30 phút)
    @Scheduled(cron = "0 */30 * * * *")
    public void remindCoachesAboutUpcomingBookings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inOneHour = now.plusHours(1);
        LocalDateTime inTwoHours = now.plusHours(2);

        List<Booking> upcomingBookings = bookingRepository.findByScheduledTimeBetween(inOneHour, inTwoHours);

        for (Booking booking : upcomingBookings) {
            if (booking.getStatus().name().equals("BOOKED")) {
                User coach = booking.getSchedule().getCoach();
                String clientName = booking.getUser().getName();
                String timeSlot = booking.getSchedule().getSlot().getLabel();
                
                notificationService.createNotification(
                        coach,
                        "👨‍⚕️ Nhắc nhở lịch tư vấn",
                        String.format("Bạn có lịch tư vấn với khách hàng %s trong 1-2 giờ tới (%s). " +
                                "Vui lòng chuẩn bị và tham gia đúng giờ. " +
                                "Ghi chú từ khách hàng: %s",
                                clientName, timeSlot, 
                                booking.getNotes() != null && !booking.getNotes().isEmpty() 
                                    ? booking.getNotes() : "Không có ghi chú")
                );
            }
        }
    }

    // Kiểm tra và nhắc nhở về lịch hẹn đã quá hạn (chạy vào 10h sáng mỗi ngày)
    @Scheduled(cron = "0 0 10 * * *")
    public void checkMissedBookings() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime now = LocalDateTime.now();

        List<Booking> missedBookings = bookingRepository.findByScheduledTimeBetween(yesterday, now);

        for (Booking booking : missedBookings) {
            if (booking.getStatus().name().equals("BOOKED")) {
                String coachName = booking.getSchedule().getCoach().getName();
                
                notificationService.createNotification(
                        booking.getUser(),
                        "⚠️ Lịch hẹn đã qua",
                        String.format("Bạn đã bỏ lỡ buổi tư vấn với chuyên gia %s. " +
                                "Đừng lo lắng, hãy đặt lịch hẹn mới để tiếp tục được hỗ trợ " +
                                "trong hành trình cai thuốc lá của bạn.",
                                coachName)
                );
                
                // Cập nhật trạng thái booking thành FINISHED để tránh nhắc lại
                booking.setStatus(org.Scsp.com.Enum.BookingStatus.FINISHED);
                bookingRepository.save(booking);
            }
        }
    }

}
