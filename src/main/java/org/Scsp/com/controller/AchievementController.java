package org.Scsp.com.controller;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.AchievementDTO;
import org.Scsp.com.model.Achievement;
import org.Scsp.com.repository.AchievementRepository;
import org.Scsp.com.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@AllArgsConstructor
public class AchievementController {


    private AchievementService achievementService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<AchievementDTO>> getAchievementById(@PathVariable Long userId) {
        return ResponseEntity.ok(achievementService.getUserAchievements(userId));
    }
}