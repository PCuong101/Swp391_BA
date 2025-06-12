package org.Scsp.com.service;

import org.Scsp.com.dto.AchievementDTO;

import java.util.List;

public interface AchievementService {
    List<AchievementDTO> getUserAchievements(Long userId);
    void checkAndUpdateAchievements(Long userId);
}