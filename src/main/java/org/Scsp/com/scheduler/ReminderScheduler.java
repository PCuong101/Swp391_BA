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


    @Scheduled(cron = "*/10 * * * * * ") // Mỗi giờ
    public void remindUpcomingBookings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inOneHour = now.plusHours(1);
        LocalDateTime inOneDay = now.plusDays(1);

        // Nhắc trước 1 giờ
        List<Booking> inOneHourBookings = bookingRepository.findByScheduledTimeBetween(now, inOneHour);

        for (Booking booking : inOneHourBookings) {
            notificationService.createNotification(
                    booking.getUser(),
                    "Lịch hẹn sắp diễn ra",
                    "Bạn có lịch hẹn trong 1 giờ. Vui lòng chuẩn bị!"
            );
        }

        // Nhắc trước 1 ngày
        List<Booking> inOneDayBookings = bookingRepository.findByScheduledTimeBetween(now, inOneDay);

        for (Booking booking : inOneDayBookings) {
            notificationService.createNotification(
                    booking.getUser(),
                    "Lịch hẹn ngày mai",
                    "Bạn có lịch hẹn trong 24 giờ tới. Kiểm tra chi tiết!"
            );
        }
    }

}
