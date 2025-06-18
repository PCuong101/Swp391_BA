package org.Scsp.com.service;


import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.model.QuitPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HealthMilestoneService {
    void createHealthMilestones(QuitPlan plan);
    List<MilestoneProgressDTO> getMilestoneProgress(Long planId);
    void autoMarkAchievedMilestones();
}
