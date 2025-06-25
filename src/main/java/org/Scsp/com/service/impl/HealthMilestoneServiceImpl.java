package org.Scsp.com.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Scsp.com.data.MilestoneTemplateProvider;
import org.Scsp.com.dto.MilestoneDTO;
import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.model.HealthMilestone;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.UserDailyLog;
import org.Scsp.com.repository.HealthMilestoneRepository;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserDailyLogsRepository;
import org.Scsp.com.service.HealthMilestoneService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

        List<HealthMilestone> healthMilestone = buildMilestonesWithWeight(templates, startDate, plan);

        List<HealthMilestone> saved = healthMilestoneRepository.saveAll(healthMilestone);
        plan.setMilestones(saved);
    }

    private List<HealthMilestone> buildMilestonesWithWeight(List<MilestoneDTO> templates, LocalDateTime startDate, QuitPlan plan) {
        List<Duration> offsets = templates.stream()
                .map(MilestoneDTO::getOffset)
                .toList();

        List<Double> weights = calculateWeights(offsets);

        List<HealthMilestone> result = new ArrayList<>();

        for (int i = 0; i < templates.size(); i++) {
            MilestoneDTO template = templates.get(i);
            double weight = weights.get(i);
            LocalDateTime expected = startDate.plus(template.getOffset());

            HealthMilestone milestone = new HealthMilestone();
            milestone.setName(template.getName());
            milestone.setDescription(template.getDescription());
            milestone.setExpectedDate(expected);
            milestone.setQuitPlan(plan);
            milestone.setWeight(weight);

            result.add(milestone);
        }
        return result;
    }

    private List<Double> calculateWeights(List<Duration> offsets) {
        List<Long> durationsInMinutes = offsets.stream()
                .map(Duration::toMinutes)
                .toList();

        // Trọng số ngược log(phút)
        List<Double> rawWeights = durationsInMinutes.stream()
                .map(d -> 1.0 / Math.sqrt(d)) // tránh log(0)
                .toList();

        double totalRawWeight = rawWeights.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return rawWeights.stream()
                .map(raw -> raw / totalRawWeight) // chuẩn hóa về tổng = 1
                .toList();
    }


    @Override
    public List<MilestoneProgressDTO> getMilestoneProgress(Long userId) {
        QuitPlan quitPlan = quitPlanRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Quit plan not found with id: " + userId));
        if (quitPlan.getMilestones() == null || quitPlan.getMilestones().isEmpty()) {
            return new ArrayList<>(); // Không có milestone nào
        }
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        LocalDateTime now = LocalDateTime.now();

        List<HealthMilestone> healthMilestones = healthMilestoneRepository.findByQuitPlan(quitPlan);

        // Lấy log ngày hôm nay nếu có
        UserDailyLog todayLog = userDailyLogsRepository.findByQuitPlan_PlanIDAndLogDateBetween(quitPlan.getPlanID(), startOfDay, endOfDay);
        boolean smokedToday = todayLog != null && Boolean.TRUE.equals(todayLog.getSmokedToday());


        List<MilestoneProgressDTO> result = new ArrayList<>();

        for (HealthMilestone milestone : healthMilestones) {
            boolean achieved = milestone.isAchieved();
            LocalDateTime expectedDate = milestone.getExpectedDate();

            // Nếu milestone đã đến hạn, đánh dấu đạt
            if (!achieved && !expectedDate.isAfter(now)) {
                milestone.setAchieved(true);
                achieved = true;
            }

            int percent = calculateMilestoneProgress(milestone);
            LocalDateTime recoveryEndTime = null;


            if (achieved) {
                if (smokedToday) {
                    // Tính trọng số ảnh hưởng của milestone
                    double weight = milestone.getWeight();
                    // Trọng số càng lớn thì milestone cần giảm mạnh để cảnh cáo
                    double baseDeduction = 100; // giới hạn max bị trừ (ví dụ 70%)
                    int deduction = (int) Math.round(weight * baseDeduction);


                    // Tính thời gian phục hồi dựa trên % bị trừ
                    long totalMinutes = Duration.between(quitPlan.getStartDate(), milestone.getExpectedDate()).toMinutes();
                    long lostMinutes = (long) (totalMinutes * deduction / 100.0);

                    // Thời gian bắt đầu tính hồi phục
                    LocalDateTime recoveryStart = todayLog.getLogDate();

                    // Tính thời gian đã trôi qua kể từ khi bắt đầu hồi phục
                    Duration elapsedRecovery = Duration.between(recoveryStart, now);
                    long elapsedMinutes = elapsedRecovery.toMinutes();
                    long remainingMinutes = Math.max(0, lostMinutes - elapsedMinutes);
                    int remainingPercent = Math.round((float) (remainingMinutes * 100) / totalMinutes);
                    percent = Math.max(0, percent - remainingPercent);
                    log.debug("Milestone {}: smoked today, reducing progress by {}%, remaining progress: {}", milestone.getName(), remainingPercent, percent);
                    if (remainingMinutes > 0) {
                        recoveryEndTime = now.plusMinutes(remainingMinutes); // ✅ quan trọng
                    }
                }
            } else {
                recoveryEndTime = milestone.getExpectedDate();
            }

            result.add(MilestoneProgressDTO.builder()
                    .name(milestone.getName())
                    .progressPercent(percent)
                    .recoveryEndTime(recoveryEndTime != null ? recoveryEndTime.truncatedTo(ChronoUnit.MINUTES) : null)
                    .achieved(achieved)
                    .build());
        }
        healthMilestoneRepository.saveAll(healthMilestones);

        return result;
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
