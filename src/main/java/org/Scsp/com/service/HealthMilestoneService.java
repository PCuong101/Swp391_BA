package org.Scsp.com.service;


import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.model.QuitPlans;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HealthMilestoneService {
    void createHealthMilestones(QuitPlans plan);
    List<MilestoneProgressDTO> getMilestoneProgress(Long planId);
    void autoMarkAchievedMilestones();
}
