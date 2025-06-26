package org.Scsp.com.service;

import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.dto.ScheduleOverviewDTO;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    List<ScheduleDTO> getCoachSchedules(Long coachId);

}
