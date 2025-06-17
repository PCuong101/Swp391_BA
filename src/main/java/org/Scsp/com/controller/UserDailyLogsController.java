package org.Scsp.com.controller;

import lombok.RequiredArgsConstructor;
import org.Scsp.com.dto.UserDailyLogsDto;
import org.Scsp.com.model.UserDailyLog;
import org.Scsp.com.service.UserDailyLogsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-daily-logs")
@RequiredArgsConstructor
public class UserDailyLogsController {

    private final UserDailyLogsService userDailyLogsService;

    @PostMapping("/create-daily-logs")
    public ResponseEntity<?> createUserDailyLog(@RequestBody UserDailyLogsDto userDailyLogsDto) {
        try {
            UserDailyLogsDto savedLog = userDailyLogsService.createUserDailyLog(userDailyLogsDto);
            return ResponseEntity.ok(savedLog);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @GetMapping("/get-daily-logs/{userId}")
    public ResponseEntity<List<UserDailyLog>> getUserDailyLog(@PathVariable Long userId) {
        Long planId = userDailyLogsService.getPlanIdByUserId(userId);
        List<UserDailyLog> userDailyLogs = userDailyLogsService.getUserDailyLogs(planId);
        if (userDailyLogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userDailyLogs);
    }

    @DeleteMapping("/delete-daily-log/{logId}")
    public ResponseEntity<String> deleteUserDailyLog(@PathVariable Long logId) {
        userDailyLogsService.deleteUserDailyLog(logId);
        return ResponseEntity.ok("User daily log deleted successfully.");
    }
}