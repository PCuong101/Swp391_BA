package org.Scsp.com.controller;

import org.Scsp.com.dto.MemberPlanDTO;
import org.Scsp.com.model.MemberPlan;
import org.Scsp.com.service.MemberPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member-plans")
public class MemberPlanController {

    @Autowired
    private MemberPlanService memberPlanService;

    @GetMapping("/list")
    public List<MemberPlan> listMemberPlans() {
        // This method should return a list of member plans.
        // For now, we return a simple message.
        return memberPlanService.getAllPlans();
    }

    @PutMapping("/update/{planID}")
    public ResponseEntity<?> updateMemberPlan(@RequestBody MemberPlanDTO memberPlan) {
        return ResponseEntity.ok(memberPlanService.updateMemberPlan(memberPlan));
    }
}
