package org.Scsp.com.controller;

import org.Scsp.com.dto.CoachDTO;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.ScheduleRepository;
import org.Scsp.com.service.ScheduleService;
import org.Scsp.com.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleRepository scheduleRepository;
    private final UsersService usersService;
    private final ScheduleService scheduleService;

    public ScheduleController(UsersService usersService, ScheduleService scheduleService) {
        this.usersService = usersService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/get-schedule")
    public ResponseEntity<List<CoachDTO>> getSchedule() {
        List<Long> coachIds = usersService.getAllCoachIds();
        List<CoachDTO> coachs = new ArrayList<>();

        for (Long coachId : coachIds) {
            Optional<User> coachOpt = usersService.getUserById(coachId);

            if (coachOpt.isPresent()) {
                User coach = coachOpt.get();
                List<ScheduleDTO> schedules = scheduleService.getPublishedSchedules(coachId);

                coachs.add(new CoachDTO(
                        coach.getUserId(),
                        coach.getName(),
                        coach.getEmail(),
                        coach.getProfilePicture(),
                        schedules
                ));
            }
        }

        return ResponseEntity.ok(coachs);
    }

    @PutMapping("/{id}/publish")
    public String publishSchedule(@PathVariable Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (schedule.isPublished()) {
            return "⛔ Lịch đã được công khai trước đó.";
        }

        schedule.setPublished(true);
        scheduleRepository.save(schedule);
        return "✅ Lịch đã được công khai thành công.";
    }
}
