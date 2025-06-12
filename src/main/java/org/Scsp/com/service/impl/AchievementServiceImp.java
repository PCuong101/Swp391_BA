package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.Enum.CustomLogicKey;
import org.Scsp.com.dto.AchievementDTO;
import org.Scsp.com.model.Achievement;
import org.Scsp.com.model.AchievementTemplate;
import org.Scsp.com.model.QuitPlans;
import org.Scsp.com.repository.AchievementRepository;
import org.Scsp.com.repository.AchievementTempRepository;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserDailyLogsRepository;
import org.Scsp.com.service.AchievementService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AchievementServiceImp implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserDailyLogsRepository userDailyLogsRepository;
    private final QuitPlanRepository quitPlanRepository;
    private final AchievementTempRepository achievementTempRepository;

    private AchievementDTO toDto(Achievement a) {
        return AchievementDTO.builder()
                .name(a.getTemplate().getTitle())
                .description(a.getTemplate().getDescription())
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
        QuitPlans plan = quitPlanRepository.findLatestByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("No quit plan found"));

        List<AchievementTemplate> templates = achievementTempRepository.findAll();

        List<Achievement> existingAchievements  = achievementRepository.findByUser_UserId(userId);
        List<CustomLogicKey> existingKeys  = existingAchievements .stream()
                .map(a -> a.getTemplate().getCustomLogicKey())
                .toList();
        List<Achievement> newAchievements = new ArrayList<>();
        for (AchievementTemplate template : templates) {
            CustomLogicKey customLogicKey = template.getCustomLogicKey();
            if (existingKeys.contains(customLogicKey)) {
                continue; // Achievement already exists
            }
            if (shouldUnlock(customLogicKey, plan)) {
                Achievement achievement = new Achievement();
                achievement.setUser(plan.getUser());
                achievement.setTemplate(template);
                achievement.setDateAchieved(LocalDateTime.now());
                newAchievements.add(achievement);
            }
        }
        if (!newAchievements.isEmpty()) {
            achievementRepository.saveAll(newAchievements);
        }
    }

    private boolean shouldUnlock(CustomLogicKey key, QuitPlans plan) {
        LocalDate now = LocalDate.now();
        long daysSinceStart = ChronoUnit.DAYS.between(plan.getStartDate().toLocalDate(), now);

        return switch (key) {
            case FIRST_DAY -> daysSinceStart >= 1;
            case DAYS_QUIT_SMOKING_14 -> daysSinceStart >= 14;
            case DAYS_QUIT_SMOKING_30 -> daysSinceStart >= 30;
            case MONEY_SAVED_100K -> plan.getMoneySaved().compareTo(new BigDecimal("100000")) >= 0;
            case MONEY_SAVED_500K -> plan.getMoneySaved().compareTo(new BigDecimal("500000")) >= 0;
            case MONEY_SAVED_1M -> plan.getMoneySaved().compareTo(new BigDecimal("1000000")) >= 0;
            case MONEY_SAVED_5M -> plan.getMoneySaved().compareTo(new BigDecimal("5000000")) >= 0;

//            case STREAK_NO_SMOKE_1:
//                return checkStreakNoSmoke(plan.getPlanId(), 1);
//
//            case STREAK_NO_SMOKE_7:
//                return checkStreakNoSmoke(plan.getPlanId(), 7);
//
//            case STREAK_NO_SMOKE_30:
//                return checkStreakNoSmoke(plan.getPlanId(), 30);
//
//            case NO_RELAPSE_14_DAYS:
//                return checkNoRelapseDays(plan.getPlanId(), 14);

            default -> false;
        };
    }
//    private boolean checkStreakNoSmoke(Long planId, int days) {
//        List<Boolean> recent = dailyLogsRepository
//                .findRecentLogs(planId, days)
//                .stream()
//                .map(log -> Boolean.FALSE.equals(log.getSmokedToday()))
//                .collect(Collectors.toList());
//
//        return recent.size() == days && recent.stream().allMatch(Boolean::booleanValue);
//    }
//
//    private boolean checkNoRelapseDays(Long planId, int days) {
//        List<Boolean> recent = dailyLogsRepository
//                .findRecentLogs(planId, days)
//                .stream()
//                .map(log -> Boolean.FALSE.equals(log.getSmokedToday()))
//                .collect(Collectors.toList());
//
//        return recent.size() == days && recent.stream().noneMatch(smoked -> smoked == false);
//    }


}

