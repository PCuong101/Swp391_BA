// src/main/java/org/Scsp/com/service/QuitPlanService.java
package org.Scsp.com.service;

import org.Scsp.com.controller.SavingResponseDto;
import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface QuitPlansService {
    List<QuitPlan> findAll();
    QuitPlan findById(Long id);
    QuitPlan createPlan(QuitPlanDto quitPlanDto);
    void deleteById(Long id);
    SavingResponseDto getSavingsByUserId(Long userId);
}