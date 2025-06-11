package org.Scsp.com.service.impl;

import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.model.HealthMilestone;
import org.Scsp.com.model.MilestoneTemplate;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.repository.HealthMilestoneRepository;
import org.Scsp.com.service.HealthMilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HealthMilestoneImpl implements HealthMilestoneService {


    private final  HealthMilestoneRepository healthMilestoneRepository;

    List<MilestoneTemplate> templates = List.of(
            new MilestoneTemplate("Ổn định nhịp tim", "Nhịp tim bình thường", Duration.ofMinutes(20)),
            new MilestoneTemplate("CO giảm", "CO trong máu giảm", Duration.ofHours(8)),
            new MilestoneTemplate("Hô hấp cải thiện", "Phổi bắt đầu hồi phục", Duration.ofDays(2)),
            new MilestoneTemplate("Vị giác tốt hơn", "Cảm nhận vị rõ hơn", Duration.ofDays(3)),
            new MilestoneTemplate("Phổi sạch dần", "Ít ho, dễ thở", Duration.ofDays(14)),
            new MilestoneTemplate("Tuần hoàn tốt hơn", "Lưu thông máu cải thiện", Duration.ofDays(30)),
            new MilestoneTemplate("Giảm nguy cơ đột quỵ", "Nguy cơ tim mạch giảm", Duration.ofDays(365))
    );

    @Autowired
    public HealthMilestoneImpl(HealthMilestoneRepository healthMilestoneRepository) {
        this.healthMilestoneRepository = healthMilestoneRepository;
    }

    @Override
    public void createHealthMilestones(QuitPlan plan) {
        LocalDateTime startDate = plan.getStartDate();
        List<HealthMilestone> milestoneList= new ArrayList<>();

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
        plan.setMilestones(milestoneList);
        healthMilestoneRepository.saveAll(milestoneList);
    }

    @Override
    public List<MilestoneProgressDTO> getMilestoneProgress(Long planId) {
        return List.of();
    }
}
