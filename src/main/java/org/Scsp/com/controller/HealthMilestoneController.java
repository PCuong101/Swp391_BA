package org.Scsp.com.controller;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.service.HealthMilestoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/health-milestones")
public class HealthMilestoneController {
    private HealthMilestoneService healthMilestoneService;

    @GetMapping("/progress/{planId}")
    public ResponseEntity<List<MilestoneProgressDTO>> getMilestoneProgress(@PathVariable Long planId) {
        return ResponseEntity.ok(healthMilestoneService.getMilestoneProgress(planId));
    }
}
