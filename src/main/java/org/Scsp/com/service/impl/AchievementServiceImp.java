package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.Enum.CustomLogicKey;
import org.Scsp.com.dto.AchievementDTO;
import org.Scsp.com.model.*;
import org.Scsp.com.repository.AchievementRepository;
import org.Scsp.com.repository.AchievementTempRepository;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserDailyLogsRepository;
import org.Scsp.com.service.AchievementService;
import org.Scsp.com.service.QuitPlansService;
import org.Scsp.com.service.TaskService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class AchievementServiceImp implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final QuitPlanRepository quitPlanRepository;
    private final QuitPlansService quitPlansService;
    private final AchievementTempRepository achievementTempRepository;
    private final UserDailyLogsRepository userDailyLogsRepository;
    private  final TaskService taskService;
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
        LocalDate startDate = plan.getStartDate().toLocalDate();

        BigDecimal moneySaved = quitPlansService.getSavingsByUserId(plan.getUser().getUserId()).getTotalSavings();
        int diaryCount = userDailyLogsRepository.countByQuitPlan_PlanID(plan.getPlanID());
        List<UserDailyLog> userDailyLogs = userDailyLogsRepository.findByQuitPlan_PlanIDOrderByLogDateAsc(plan.getPlanID());
        int taskCompleted = numberOfTaskComplete(plan.getUser().getUserId());

        return switch (key) {
            case FIRST_DAY -> true;

            case DAYS_QUIT_SMOKING_14, DAYS_QUIT_SMOKING_30 -> {
                int requiredDays = extractSuffixNumber(key.name());
                yield daysSinceStart >= requiredDays;
            }

            case MONEY_SAVED_100K, MONEY_SAVED_500K, MONEY_SAVED_1M, MONEY_SAVED_5M -> {
                BigDecimal requiredAmount = extractMoneyAmount(key.name());
                yield moneySaved.compareTo(requiredAmount) >= 0;
            }

            case STREAK_NO_SMOKE_1, STREAK_NO_SMOKE_7, STREAK_NO_SMOKE_30 -> {
                int requiredDays = extractSuffixNumber(key.name());
                yield daysSinceStart >= requiredDays &&
                        checkStreakNoSmoke(userDailyLogs, startDate, requiredDays);
            }

            case NUMBER_OF_DIARY_1, NUMBER_OF_DIARY_7, NUMBER_OF_DIARY_30 -> {
                int required = extractSuffixNumber(key.name());
                yield diaryCount >= required;
            }

            case NUMBER_OF_TASK_COMPLETE_5, NUMBER_OF_TASK_COMPLETE_10,
                 NUMBER_OF_TASK_COMPLETE_20, NUMBER_OF_TASK_COMPLETE_30,
                 NUMBER_OF_TASK_COMPLETE_50, NUMBER_OF_TASK_COMPLETE_100 -> {
                int required = extractSuffixNumber(key.name());
                yield taskCompleted >= required;
            }
            default -> false;
        };
    }

    private int extractSuffixNumber(String keyName) {
        // Tách phần cuối là số, ví dụ: _14, _7, _30
        Matcher matcher = Pattern.compile("_(\\d+)$").matcher(keyName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("Không tìm thấy số trong key: " + keyName);
    }

    private BigDecimal extractMoneyAmount(String keyName) {
        return switch (keyName) {
            case "MONEY_SAVED_100K" -> new BigDecimal("100000");
            case "MONEY_SAVED_500K" -> new BigDecimal("500000");
            case "MONEY_SAVED_1M"   -> new BigDecimal("1000000");
            case "MONEY_SAVED_5M"   -> new BigDecimal("5000000");
            default -> throw new IllegalArgumentException("Không hỗ trợ key: " + keyName);
        };
    }



    private boolean checkStreakNoSmoke(List<UserDailyLog> userDailyLogs,LocalDate startDay ,int requiredDays) {
        if (userDailyLogs.isEmpty()) return false;

        // B1: Map ngày -> smokedToday
        Map<LocalDate, Boolean> logMap = new HashMap<>();
        for (UserDailyLog log : userDailyLogs) {
            logMap.put(log.getLogDate().toLocalDate(), log.getSmokedToday());
        }

        LocalDate end = LocalDate.now();

        int currentStreak = 0;
        int maxStreak = 0;

        for (LocalDate date = startDay; !date.isAfter(end); date = date.plusDays(1)) {
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

    private int numberOfTaskComplete(Long userId){
        return taskService.getCompletedTasksByUserId(userId).size();
    }

}

