package org.Scsp.com.scheduler;

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


    public void generateWeeklySchedules(Long coachId) {
        User coach = userRepo.findUserByUserId(coachId);
        List<Slot> slots = slotRepo.findAll();

        LocalDate today = LocalDate.now();
        for (int i = 0; i < 14; i++) {
            LocalDate targetDate = today.plusDays(i);
                for (Slot slot : slots) {
                    boolean exists = scheduleRepo.existsByCoachUserIdAndSlotSlotIDAndDate(
                            coach.getUserId(), slot.getSlotID(), targetDate);


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

        System.out.println("📅 Tạo lịch tuần cho các coach thành công!");
    }
}
