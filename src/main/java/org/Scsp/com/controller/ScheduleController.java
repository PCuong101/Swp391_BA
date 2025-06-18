package org.Scsp.com.controller;

import org.Scsp.com.dto.CoachDTO;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.model.User;
import org.Scsp.com.service.ScheduleService;
import org.Scsp.com.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final UsersService usersService;
    private final ScheduleService scheduleService;

    public ScheduleController(UsersService usersService, ScheduleService scheduleService) {
        this.usersService = usersService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/get-schedule")
    public ResponseEntity<List<CoachDTO>> getSchedule() {
        List<Long> coachIds = usersService.getAllCoachIds(); // Example coach IDs
        List<ScheduleDTO> schedules = new ArrayList<>();
        List<CoachDTO> coachs = new ArrayList<>();
        for (Long coachId : coachIds) {
            Optional<User> coach = usersService.getUserById(coachId);

            schedules = scheduleService.getCoachSchedules(coachId);
            coachs.add(new CoachDTO(coach.get().getUserId(), coach.get().getName(), coach.get().getEmail(), coach.get().getProfilePicture(), schedules));
        }
        return ResponseEntity.ok(coachs);
    }
}
