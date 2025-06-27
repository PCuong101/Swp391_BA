package org.Scsp.com.controller;

import lombok.RequiredArgsConstructor;
import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.dto.SavingResponseDto;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.service.QuitPlansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // Use @RequiredArgsConstructor for final fields
@RequestMapping("/api/quit-plan")
public class QuitPlansController {

    private final QuitPlansService quitPlansService;

    @PostMapping
    public ResponseEntity<QuitPlan> createQuitPlan(
            @RequestBody QuitPlanDto quitPlanDto
    ) {
        return ResponseEntity.ok( quitPlansService.createPlan(quitPlanDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<QuitPlan> getQuitPlanById(@PathVariable Long id) {
        QuitPlan quitPlan = quitPlansService.findById(id);
        if (quitPlan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quitPlan);
    }
    @GetMapping("{userId}/savings")
    public ResponseEntity<SavingResponseDto> getSavingsByUserId(@PathVariable Long userId) {
        SavingResponseDto savings = quitPlansService.getSavingsByUserId(userId);
        if (savings == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(savings);
    }
}
