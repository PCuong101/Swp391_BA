package org.Scsp.com.service;

import org.Scsp.com.dto.AchievementDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AchievementService {
    List<AchievementDTO> getUserAchievements(Long userId);
    void checkAndUpdateAchievements(Long userId);


}