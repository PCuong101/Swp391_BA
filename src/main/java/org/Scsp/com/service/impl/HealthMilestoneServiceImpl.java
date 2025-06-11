package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.model.HealthMilestone;
import org.Scsp.com.model.MilestoneTemplate;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.repository.HealthMilestoneRepository;
import org.Scsp.com.repository.QuitPlanRepository;
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




    @Override
    public void createHealthMilestones(QuitPlan plan) {
        List<MilestoneTemplate> templates = List.of(
                new MilestoneTemplate("Ổn định nhịp tim", "Nhịp tim bình thường", Duration.ofMinutes(20)),
                new MilestoneTemplate("CO giảm", "CO trong máu giảm", Duration.ofHours(8)),
                new MilestoneTemplate("Hô hấp cải thiện", "Phổi bắt đầu hồi phục", Duration.ofDays(2)),
                new MilestoneTemplate("Vị giác tốt hơn", "Cảm nhận vị rõ hơn", Duration.ofDays(3)),
                new MilestoneTemplate("Phổi sạch dần", "Ít ho, dễ thở", Duration.ofDays(14)),
                new MilestoneTemplate("Tuần hoàn tốt hơn", "Lưu thông máu cải thiện", Duration.ofDays(30)),
                new MilestoneTemplate("Giảm nguy cơ đột quỵ", "Nguy cơ tim mạch giảm", Duration.ofDays(365))
        );

        LocalDateTime startDate = plan.getStartDate();
        List<HealthMilestone> milestoneList = new ArrayList<>();

        for (MilestoneTemplate template : templates) {
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

        List<HealthMilestone> milestoneProgress = healthMilestoneRepository.findByQuitPlan(quitPlan);

        List<MilestoneProgressDTO> progressList = new ArrayList<>();
        if (milestoneProgress != null && !milestoneProgress.isEmpty()) {
            for (HealthMilestone milestone : milestoneProgress) {
                MilestoneProgressDTO progressDTO = MilestoneProgressDTO.builder()
                        .name(milestone.getName())
                        .expectedDate(milestone.getExpectedDate())
                        .progressPercent(calculateMilestoneProgress(milestone))
                        .achieved(milestone.isAchieved())
                        .build();
                progressList.add(progressDTO);
            }
        }

        return progressList;
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
