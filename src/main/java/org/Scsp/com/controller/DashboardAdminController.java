package org.Scsp.com.controller;


import lombok.RequiredArgsConstructor;
import org.Scsp.com.dto.CoachDTO;
import org.Scsp.com.dto.DashboardSummaryDto;
import org.Scsp.com.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashboardAdminController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getSummary() {
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("coaches")
    public ResponseEntity<List<CoachDTO>> getCoachs() {
        List<CoachDTO> coaches = dashboardService.getCoaches();
        if (coaches == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coaches);
    }

    @GetMapping("/coach/{id}")
    public ResponseEntity<CoachDTO> getCoachById(@PathVariable Long id) {
        CoachDTO coach = dashboardService.getCoachById(id);
        if (coach == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coach);
    }
}
