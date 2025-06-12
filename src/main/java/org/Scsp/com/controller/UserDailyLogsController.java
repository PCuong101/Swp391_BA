package org.Scsp.com.controller;

import lombok.RequiredArgsConstructor;
import org.Scsp.com.dto.UserDailyLogsDto;
import org.Scsp.com.service.UserDailyLogsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-daily-logs")
@RequiredArgsConstructor
public class UserDailyLogsController {

    private final UserDailyLogsService userDailyLogsService;

    @PostMapping
    public ResponseEntity<UserDailyLogsDto> createUserDailyLog(@RequestBody UserDailyLogsDto userDailyLogsDto) {
        UserDailyLogsDto savedLog = userDailyLogsService.createUserDailyLog(userDailyLogsDto);
        return ResponseEntity.ok(savedLog);
    }
}