package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.UserDailyLogsDto;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.UserDailyLogs;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserDailyLogsRepository;
import org.Scsp.com.service.HealthMilestoneService;
import org.Scsp.com.service.UserDailyLogsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserDailyLogsImpl implements UserDailyLogsService {

    private final UserDailyLogsRepository userDailyLogsRepository;
    private final QuitPlanRepository quitPlanRepository;

    @Override
    public UserDailyLogsDto createUserDailyLog(UserDailyLogsDto newLogDto) {

        QuitPlan quitPlan = quitPlanRepository.findById(newLogDto.getQuitPlanId())
                .orElseThrow(() -> new RuntimeException("Quit plan not found with id: " + newLogDto.getQuitPlanId()));

        UserDailyLogs userDailyLogs = UserDailyLogs.builder()
                .logDate(newLogDto.getLogDate())
                .smokedToday(newLogDto.isSmokedToday())
                .quitPlan(quitPlan)
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
