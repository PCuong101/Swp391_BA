package org.Scsp.com.scheduler;

import jakarta.annotation.PostConstruct;
import org.Scsp.com.Enum.Role;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.model.Slot;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.ScheduleRepository;
import org.Scsp.com.repository.SlotRepository;
import org.Scsp.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DailyScheduleGenerator {

    @Autowired private UsersRepository userRepo;
    @Autowired private SlotRepository slotRepo;
    @Autowired private ScheduleRepository scheduleRepo;

    // Hàm này sẽ chạy khi ứng dụng khởi động
    @PostConstruct
    public void generateWeeklySchedules() {
        List<User> coaches = userRepo.findByRole(Role.COACH); // Đổi tuỳ cột phân quyền bạn dùng
        List<Slot> slots = slotRepo.findAll();

        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            LocalDate targetDate = today.plusDays(i);
            for (User coach : coaches) {
                for (Slot slot : slots) {
                    boolean exists = scheduleRepo.existsByCoachUserIDAndSlotSlotIDAndDate(
                            coach.getUserID(), slot.getSlotID(), targetDate);

                    if (!exists) {
                        Schedule schedule = new Schedule();
                        schedule.setCoach(coach);
                        schedule.setSlot(slot);
                        schedule.setDate(targetDate);
                        schedule.setAvailable(true);
                        scheduleRepo.save(schedule);
                    }
                }
            }
        }

        System.out.println("📅 Tạo lịch tuần cho các coach thành công!");
    }
}
