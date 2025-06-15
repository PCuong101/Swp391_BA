package org.Scsp.com.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.Scsp.com.data.MilestoneTemplateProvider;
import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.model.HealthMilestone;
import org.Scsp.com.dto.MilestoneDTO;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.UserDailyLog;
import org.Scsp.com.repository.HealthMilestoneRepository;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserDailyLogsRepository;
import org.Scsp.com.service.HealthMilestoneService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HealthMilestoneServiceImpl implements HealthMilestoneService {


    private final HealthMilestoneRepository healthMilestoneRepository;
    private final QuitPlanRepository quitPlanRepository;
    private final MilestoneTemplateProvider templatesProvider;
    private final UserDailyLogsRepository userDailyLogsRepository;

    @Override
    public void createHealthMilestones(QuitPlan plan) {
        List<MilestoneDTO> templates = templatesProvider.getTemplates();

        LocalDateTime startDate = plan.getStartDate();
        List<HealthMilestone> milestoneList = new ArrayList<>();

        for (MilestoneDTO template : templates) {
            LocalDateTime expected = startDate.plus(template.getOffset());

            HealthMilestone milestone = new HealthMilestone();
            milestone.setName(template.getName());
            milestone.setDescription(template.getDescription());
            milestone.setExpectedDate(expected);
            milestone.setQuitPlan(plan);
            milestone.setOriginalExpectedDate(expected);
            milestoneList.add(milestone);
        }

        List<HealthMilestone> healthMilestone = healthMilestoneRepository.saveAll(milestoneList);
        plan.setMilestones(healthMilestone);
    }

    @Override
    public List<MilestoneProgressDTO> getMilestoneProgress(Long planId) {
        QuitPlan quitPlan = quitPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Quit plan not found with id: " + planId));

        LocalDateTime now = LocalDateTime.now();
        List<HealthMilestone> healthMilestones = healthMilestoneRepository.findByQuitPlan(quitPlan);

        // Lấy log ngày hôm nay nếu có
        UserDailyLog todayLog = userDailyLogsRepository.findByQuitPlan_PlanIDAndLogDate(planId, now);
        boolean smokedToday = todayLog != null && Boolean.TRUE.equals(todayLog.getSmokedToday());

        List<MilestoneProgressDTO> result = new ArrayList<>();

        for (HealthMilestone milestone : healthMilestones) {
            boolean achieved = milestone.isAchieved();
            LocalDateTime expectedDate = milestone.getExpectedDate();

            if (!achieved && (expectedDate.isBefore(now) || expectedDate.isEqual(now))) {
                milestone.setAchieved(true);
                achieved = true;
            }

            int percent = calculateMilestoneProgress(milestone);
            String timeLeft;

            if (achieved) {
                if (smokedToday) {
                    // Tính phần trăm bị trừ
                    int smoked = todayLog.getCigarettesSmoked();
                    int deduction = smoked * 1;
                    percent = Math.max(0, percent - deduction);

                    //Tính thời gian cần để phục hồi milestone:
                    long totalMinutes = Duration.between(quitPlan.getStartDate(), milestone.getExpectedDate()).toMinutes();
                    long lostMinutes = totalMinutes * deduction / 100;

                    // Thời gian bắt đầu tính hồi phục
                    LocalDateTime recoveryTime = todayLog.getLogDate();

                    // Tính thời gian đã trôi qua kể từ khi bắt đầu hồi phục
                    Duration elapsedRecovery = Duration.between(recoveryTime, now);
                    long elapsedMinutes = elapsedRecovery.toMinutes();
                    long remainingMinutes = Math.max(0, lostMinutes - elapsedMinutes);

                    timeLeft = "Time remaining: " + formatDurationReadable(Duration.ofMinutes(remainingMinutes));
                } else {
                    timeLeft = "Done";
                }
            } else {
                Duration remaining = Duration.between(now, milestone.getExpectedDate());
                timeLeft = formatDurationReadable(remaining);
            }


            MilestoneProgressDTO progressDTO = MilestoneProgressDTO.builder()
                    .name(milestone.getName())
                    .progressPercent(percent)
                    .achieved(achieved)
                    .timeRemaining(timeLeft)
                    .build();
            result.add(progressDTO);
        }
        healthMilestoneRepository.saveAll(healthMilestones); // Cập nhật trạng thái đã đạt

        return result;
    }

    private String formatDurationReadable(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("d ");
        if (hours > 0) sb.append(hours).append("h ");
        if (minutes > 0 || sb.isEmpty()) sb.append(minutes).append("m");

        return sb.toString().trim();
    }


    @Transactional
    @Override
    public void autoMarkAchievedMilestones() {
        LocalDateTime now = LocalDateTime.now();
        // Lấy tất cả milestone chưa achieved và expectedDate <= hôm nay
        List<HealthMilestone> dueMilestones = healthMilestoneRepository
                .findByAchievedFalseAndExpectedDateBefore(now);

        for (HealthMilestone milestone : dueMilestones) {
            milestone.setAchieved(true);
        }
        healthMilestoneRepository.saveAll(dueMilestones);
    }

    public int calculateMilestoneProgress(HealthMilestone milestone) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = milestone.getQuitPlan().getStartDate();
        LocalDateTime expectedDate = milestone.getExpectedDate();

        if (now.isAfter(expectedDate) || now.isEqual(expectedDate)) {
            return 100; // Milestone is complete
        }

        Duration totalDuration = Duration.between(startDate, expectedDate);
        Duration elapsedDuration = Duration.between(startDate, now);

        if (totalDuration.isZero()) {
            return 0; // Avoid division by zero
        }

        long totalMinutes = totalDuration.toMinutes();
        long elapsedMinutes = elapsedDuration.toMinutes();

        return (int) Math.min((elapsedMinutes * 100) / totalMinutes, 100);
    }


}
