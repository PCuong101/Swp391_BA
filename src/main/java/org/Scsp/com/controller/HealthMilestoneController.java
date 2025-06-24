package org.Scsp.com.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.model.User;
import org.Scsp.com.service.HealthMilestoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
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
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/progress/{userId}")
    public ResponseEntity<List<MilestoneProgressDTO>> getMilestoneProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(healthMilestoneService.getMilestoneProgress(userId));
    }



}
