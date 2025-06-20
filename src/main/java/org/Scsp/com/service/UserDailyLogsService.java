package org.Scsp.com.service;

import org.Scsp.com.dto.UserDailyLogsDto;
import org.Scsp.com.model.UserDailyLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDailyLogsService {
    UserDailyLogsDto createUserDailyLog(UserDailyLogsDto newLogDto);
    Long getPlanIdByUserId(Long userId);
    List<UserDailyLog> getUserDailyLogs(Long planId);
    void deleteUserDailyLog(Long logId);
}
