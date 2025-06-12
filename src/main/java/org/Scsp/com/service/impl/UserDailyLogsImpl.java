package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.UserDailyLogsDto;
import org.Scsp.com.model.QuitPlans;
import org.Scsp.com.model.UserDailyLogs;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserDailyLogsRepository;
import org.Scsp.com.service.UserDailyLogsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDailyLogsImpl implements UserDailyLogsService {

    private final UserDailyLogsRepository userDailyLogsRepository;
    private final QuitPlanRepository quitPlanRepository;

    @Override
    public UserDailyLogsDto createUserDailyLog(UserDailyLogsDto newLogDto) {

        QuitPlans quitPlans = quitPlanRepository.findById(newLogDto.getQuitPlanId())
                .orElseThrow(() -> new RuntimeException("Quit plan not found with id: " + newLogDto.getQuitPlanId()));

        UserDailyLogs userDailyLogs = UserDailyLogs.builder()
                .logDate(newLogDto.getLogDate())
                .smokedToday(newLogDto.isSmokedToday())
                .quitPlans(quitPlans)
                .cigarettesSmoked(newLogDto.getCigarettesSmoked())
                .notes(newLogDto.getNote())
                .build();
        userDailyLogs =userDailyLogsRepository.save(userDailyLogs);
        return UserDailyLogsDto.builder()
                .smokedToday(userDailyLogs.getSmokedToday())
                .cigarettesSmoked(userDailyLogs.getCigarettesSmoked())
                .note(userDailyLogs.getNotes())
                .logDate(userDailyLogs.getLogDate())
                .build();
    }
}
