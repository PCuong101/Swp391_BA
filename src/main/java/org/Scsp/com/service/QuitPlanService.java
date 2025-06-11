// src/main/java/org/Scsp/com/service/QuitPlanService.java
package org.Scsp.com.service;

import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlan;
import java.util.List;

public interface QuitPlanService {
    List<QuitPlan> findAll();
    QuitPlan findById(Long id);
    QuitPlan createPlane(QuitPlanDto quitPlanDto);
    void deleteById(Long id);
}