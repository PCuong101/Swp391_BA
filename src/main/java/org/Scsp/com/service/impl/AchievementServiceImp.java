package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.Enum.CustomLogicKey;
import org.Scsp.com.dto.AchievementDTO;
import org.Scsp.com.model.Achievement;
import org.Scsp.com.model.AchievementTemplate;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.UserDailyLog;
import org.Scsp.com.repository.AchievementRepository;
import org.Scsp.com.repository.AchievementTempRepository;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserDailyLogsRepository;
import org.Scsp.com.service.AchievementService;
import org.Scsp.com.service.QuitPlansService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class AchievementServiceImp implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final QuitPlanRepository quitPlanRepository;
    private final QuitPlansService quitPlansService;
    private final AchievementTempRepository achievementTempRepository;
    private final UserDailyLogsRepository userDailyLogsRepository;

    private AchievementDTO toDto(Achievement a) {
        return AchievementDTO.builder()
                .id(a.getAchievementID())
                .name(a.getAchievementTemplate().getTitle())
                .description(a.getAchievementTemplate().getDescription())
                .achievedAt(a.getDateAchieved())
                .shared(a.getShared())
                .build();
    }

    @Override
    public List<AchievementDTO> getUserAchievements(Long userId) {
        checkAndUpdateAchievements(userId);
        List<Achievement> achievements = achievementRepository.findByUser_UserId(userId);
        return achievements.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void checkAndUpdateAchievements(Long userId) {
        QuitPlan plan = quitPlanRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("No quit plan found"));

        List<AchievementTemplate> templates = achievementTempRepository.findAll();

        List<Achievement> existingAchievements = achievementRepository.findByUser_UserId(userId);
        List<CustomLogicKey> existingKeys = existingAchievements.stream().map(
                achievement -> achievement.getAchievementTemplate().getCustomLogicKey()
        ).toList();
        List<Achievement> newAchievements = new ArrayList<>();
        for (AchievementTemplate template : templates) {
            CustomLogicKey customLogicKey = template.getCustomLogicKey();
            if (existingKeys.contains(customLogicKey)) {
                continue; // Achievement already exists
            }
            if (shouldUnlock(customLogicKey, plan)) {
                Achievement achievement = new Achievement();
                achievement.setUser(plan.getUser());
                achievement.setAchievementTemplate(template);
                achievement.setDateAchieved(LocalDateTime.now());
                newAchievements.add(achievement);
            }
        }
        if (!newAchievements.isEmpty()) {
            achievementRepository.saveAll(newAchievements);
        }
    }

    private boolean shouldUnlock(CustomLogicKey key, QuitPlan plan) {
        LocalDate now = LocalDate.now();
        long daysSinceStart = ChronoUnit.DAYS.between(plan.getStartDate().toLocalDate(), now);
        BigDecimal moneySaved = quitPlansService.getSavingsByUserId(plan.getUser().getUserId()).getTotalSavings();
        List<UserDailyLog> userDailyLogs = userDailyLogsRepository.findByQuitPlan_PlanIDOrderByLogDateAsc(plan.getPlanID());
        if (key == CustomLogicKey.FIRST_DAY) {
            return daysSinceStart >= 1;

        } else if (key == CustomLogicKey.DAYS_QUIT_SMOKING_14) {
            return daysSinceStart >= 14;

        } else if (key == CustomLogicKey.DAYS_QUIT_SMOKING_30) {
            return daysSinceStart >= 30;

        } else if (key == CustomLogicKey.MONEY_SAVED_100K) {
            return moneySaved.compareTo(new BigDecimal("100000")) >= 0;

        } else if (key == CustomLogicKey.MONEY_SAVED_500K) {
            return moneySaved.compareTo(new BigDecimal("500000")) >= 0;

        } else if (key == CustomLogicKey.MONEY_SAVED_1M) {
            return moneySaved.compareTo(new BigDecimal("1000000")) >= 0;

        } else if (key == CustomLogicKey.MONEY_SAVED_5M) {
            return moneySaved.compareTo(new BigDecimal("5000000")) >= 0;

        } else if (key == CustomLogicKey.STREAK_NO_SMOKE_1) {
            if (daysSinceStart >= 1) {
                return checkStreakNoSmoke(userDailyLogs, 1);
            }
            return false;

        } else if (key == CustomLogicKey.STREAK_NO_SMOKE_7) {
            if (daysSinceStart >= 7) {
                return checkStreakNoSmoke(userDailyLogs, 7);
            }
            return false;

        } else if (key == CustomLogicKey.STREAK_NO_SMOKE_30) {
            if (daysSinceStart >= 30) {
                return checkStreakNoSmoke(userDailyLogs, 30);
            }
            return false;

        } else if (key == CustomLogicKey.NUMBER_OF_DIARY_1) {
            Integer diaryCount = userDailyLogsRepository.countByQuitPlan_PlanID(plan.getPlanID());
            return diaryCount >= 1;

        } else if (key == CustomLogicKey.NUMBER_OF_DIARY_7) {
            Integer diaryCount = userDailyLogsRepository.countByQuitPlan_PlanID(plan.getPlanID());
            return diaryCount >= 7;

        } else if (key == CustomLogicKey.NUMBER_OF_DIARY_30) {
            Integer diaryCount = userDailyLogsRepository.countByQuitPlan_PlanID(plan.getPlanID());
            return diaryCount >= 30;
        } else {
            return false;
        }
    }
    private boolean checkStreakNoSmoke(List<UserDailyLog> userDailyLogs, int requiredDays) {
        if (userDailyLogs.isEmpty()) return false;

        // B1: Map ngày -> smokedToday
        Map<LocalDate, Boolean> logMap = new HashMap<>();
        for (UserDailyLog log : userDailyLogs) {
            logMap.put(log.getLogDate().toLocalDate(), log.getSmokedToday());
        }

        // B2: Duyệt từ ngày nhỏ nhất đến lớn nhất
        LocalDate start = Collections.min(logMap.keySet());
        LocalDate end = LocalDate.now(); // hoặc Collections.max(logMap.keySet())

        int currentStreak = 0;
        int maxStreak = 0;

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            Boolean smoked = logMap.get(date);

            if (smoked != null && smoked) {
                // Có log và hút thuốc → reset
                currentStreak = 0;
            } else {
                // Không hút hoặc không có log → tiếp tục chuỗi
                currentStreak++;
                maxStreak = Math.max(maxStreak, currentStreak);
            }
        }

        return maxStreak >= requiredDays;
    }


}

