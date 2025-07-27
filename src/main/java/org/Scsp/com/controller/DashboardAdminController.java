package org.Scsp.com.controller;


import lombok.RequiredArgsConstructor;
import org.Scsp.com.dto.CoachDTO;
import org.Scsp.com.dto.DashboardSummaryDto;
import org.Scsp.com.model.User;
import org.Scsp.com.scheduler.DailyScheduleGenerator;
import org.Scsp.com.service.DashboardService;
import org.Scsp.com.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashboardAdminController {

    private final DashboardService dashboardService;

    private final UsersService usersService;

    @Autowired
    private DailyScheduleGenerator dailyScheduleGenerator;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getSummary() {
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("coaches")
    public ResponseEntity<List<CoachDTO>> getCoachs() {
        List<CoachDTO> coaches = dashboardService.getCoaches();
        if (coaches == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coaches);
    }

    @GetMapping("/coach/{id}")
    public ResponseEntity<CoachDTO> getCoachById(@PathVariable Long id) {
        CoachDTO coach = dashboardService.getCoachById(id);
        if (coach == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coach);
    }

    @PostMapping("/create-coach")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        usersService.saveUser(user);
        User createdUser = usersService.getUserById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found after creation"));
        dailyScheduleGenerator.generateWeeklySchedules(createdUser.getUserId());
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/coach/{coachId}/create-schedule")
    public ResponseEntity<String> createSchedule(@PathVariable Long coachId) {
        User user = usersService.getUserById(coachId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        dailyScheduleGenerator.generateWeeklySchedules(user.getUserId());
        return ResponseEntity.ok("Weekly schedule created successfully for user ID: " + coachId);
    }
}
