package org.Scsp.com.service;

import org.Scsp.com.dto.CoachDTO;
import org.Scsp.com.dto.DashboardSummaryDto;
import org.Scsp.com.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface DashboardService {
    DashboardSummaryDto getDashboardSummary();
    List<CoachDTO> getCoaches();
    CoachDTO getCoachById(Long id);
}
