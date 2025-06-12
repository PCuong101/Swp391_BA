package org.Scsp.com.service;

import org.Scsp.com.dto.UserDailyLogsDto;
import org.springframework.stereotype.Service;

@Service
public interface UserDailyLogsService {
    UserDailyLogsDto createUserDailyLog(UserDailyLogsDto newLogDto);
}
