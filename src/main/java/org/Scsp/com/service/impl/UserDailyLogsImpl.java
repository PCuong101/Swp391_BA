package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.UserDailyLogsDto;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.User;
import org.Scsp.com.model.UserDailyLog;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserDailyLogsRepository;
import org.Scsp.com.service.UserDailyLogsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDailyLogsImpl implements UserDailyLogsService {

    private final UserDailyLogsRepository userDailyLogsRepository;
    private final QuitPlanRepository quitPlanRepository;

    @Override
    public UserDailyLogsDto createUserDailyLog(@RequestBody UserDailyLogsDto newLogDto) {

        QuitPlan quitPlan = quitPlanRepository.findByUser_UserID(newLogDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Quit plan not found with userId: " + newLogDto.getUserId()));

        UserDailyLog userDailyLog = UserDailyLog.builder()
                .logDate(newLogDto.getLogDate())
                .mood(newLogDto.getMood())
                .cravingLevel(newLogDto.getCravingLevel())
                .smokedToday(newLogDto.getSmokedToday())
                .cigarettesSmoked(newLogDto.getCigarettesSmoked())
                .spentMoneyOnCigarettes(newLogDto.getSpentMoneyOnCigarettes())
                .notes("")
                .stressLevel(newLogDto.getStressLevel())
                .build();
        userDailyLog = userDailyLogsRepository.save(userDailyLog);
        return UserDailyLogsDto.builder()
                .smokedToday(userDailyLog.getSmokedToday())
                .cigarettesSmoked(userDailyLog.getCigarettesSmoked())
                .notes(userDailyLog.getNotes())
                .logDate(userDailyLog.getLogDate())
                .build();
    }

    @Override
    public Long getPlanIdByUserId(Long userId) {
        QuitPlan quitPlan = quitPlanRepository.findByUser_UserID(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return quitPlan.getPlanID();
    }

    @Override
    public List<UserDailyLog> getUserDailyLogs(Long planId) {
        List<UserDailyLog> userDailyLogs = userDailyLogsRepository.findByQuitPlan_PlanID(planId);
        return userDailyLogs;
    }

}
