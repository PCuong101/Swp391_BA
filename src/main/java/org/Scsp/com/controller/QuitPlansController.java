package org.Scsp.com.controller;

import lombok.RequiredArgsConstructor;
import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlans;
import org.Scsp.com.service.QuitPlansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // Use @RequiredArgsConstructor for final fields
@RequestMapping("/api/quit-plan")
public class QuitPlansController {

    private final QuitPlansService quitPlansService;

    @PostMapping
    public ResponseEntity<QuitPlans> createQuitPlan(
            @RequestBody QuitPlanDto quitPlanDto
    ) {
        return ResponseEntity.ok( quitPlansService.createPlane(quitPlanDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<QuitPlans> getQuitPlanById(@PathVariable Long id) {
        QuitPlans quitPlans = quitPlansService.findById(id);
        if (quitPlans == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quitPlans);
    }
}
