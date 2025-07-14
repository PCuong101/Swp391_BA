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
import org.Scsp.com.service.NotificationService;
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
    private final NotificationService notificationService;
    private AchievementDTO toDto(Achievement a) {
        return AchievementDTO.builder()
                .id(a.getAchievementID())
                .name(a.getAchievementTemplate().getTitle())
                .description(a.getAchievementTemplate().getDescription())
                .achievedAt(a.getDateAchieved())
                .iconUrl(a.getAchievementTemplate().getIconUrl())
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
            if (shouldUnlock(customLogicKey, template.getThreshold(), plan)) {
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
        for (Achievement a : newAchievements) {
            notificationService.createNotification(
                    a.getUser(),
                    "Bạn đã đạt thành tích mới!",
                    "Chúc mừng! Bạn đã đạt thành tích: " + a.getAchievementTemplate().getTitle()
            );
        }
    }

    private boolean shouldUnlock(CustomLogicKey key, int threshold, QuitPlan plan) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = plan.getStartDate().toLocalDate();
        long daysSinceStart = ChronoUnit.DAYS.between(startDate, now);

        BigDecimal moneySaved = quitPlansService.getSavingsByUserId(plan.getUser().getUserId()).getTotalSavings();
        int diaryCount = userDailyLogsRepository.countByQuitPlan_PlanID(plan.getPlanID());
        List<UserDailyLog> userDailyLogs = userDailyLogsRepository.findByQuitPlan_PlanIDOrderByLogDateAsc(plan.getPlanID());
        int taskCompleted = numberOfTaskComplete(plan.getUser().getUserId());

        return switch (key) {
            case FIRST_DAY -> true;

            case DAYS_QUIT_SMOKING -> daysSinceStart >= threshold;

            case MONEY_SAVED -> moneySaved.compareTo(BigDecimal.valueOf(threshold)) >= 0;

            case STREAK_NO_SMOKE ->
                    daysSinceStart >= threshold && checkStreakNoSmoke(userDailyLogs, startDate, threshold);

            case NUMBER_OF_DIARY -> diaryCount >= threshold;

            case NUMBER_OF_TASK_COMPLETE -> taskCompleted >= threshold;

            default -> false;
        };
    }


    private boolean checkStreakNoSmoke(List<UserDailyLog> userDailyLogs,LocalDate startDay ,int requiredDays) {

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

