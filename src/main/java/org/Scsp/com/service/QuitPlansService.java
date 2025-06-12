// src/main/java/org/Scsp/com/service/QuitPlanService.java
package org.Scsp.com.service;

import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlans;
import java.util.List;

public interface QuitPlansService {
    List<QuitPlans> findAll();
    QuitPlans findById(Long id);
    QuitPlans createPlane(QuitPlanDto quitPlanDto);
    void deleteById(Long id);
}