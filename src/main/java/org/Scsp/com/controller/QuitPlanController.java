package org.Scsp.com.controller;


import lombok.AllArgsConstructor;
import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.service.QuitPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/quit-plan")
public class QuitPlanController {

    private final QuitPlanService quitPlanService;

    @PostMapping("/{userId}")
    public ResponseEntity<QuitPlan> createQuitPlan(
            @RequestBody QuitPlanDto quitPlanDto
    ) {
        return ResponseEntity.ok( quitPlanService.createPlane(quitPlanDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<QuitPlan> getQuitPlanById(@PathVariable Long id) {
        QuitPlan quitPlan = quitPlanService.findById(id);
        if (quitPlan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quitPlan);
    }
}
