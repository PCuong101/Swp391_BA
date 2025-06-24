package org.Scsp.com.controller;

import org.Scsp.com.dto.MilestoneProgressDTO;
import org.Scsp.com.model.User;
import org.Scsp.com.service.HealthMilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class HealthMilestoneWebsocket {

    @Autowired
    private HealthMilestoneService healthMilestoneService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/request-progress")
    public void handleProgressRequest(Message<?> message) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);

        Object userObj = accessor.getSessionAttributes().get("user");

        if (userObj == null) {
            System.out.println("⚠️ Không tìm thấy user trong session");
            return;
        }

        User user = (User) userObj;
        Long userId = Long.valueOf(user.getUserId());

        List<MilestoneProgressDTO> progress = healthMilestoneService.getMilestoneProgress(userId);
        messagingTemplate.convertAndSend(
                "/topic/health/progress/" + accessor.getSessionId(), // gửi theo session
                progress
        );

    }

}
