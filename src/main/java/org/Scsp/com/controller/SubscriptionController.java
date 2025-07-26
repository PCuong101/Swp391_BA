package org.Scsp.com.controller;

import lombok.RequiredArgsConstructor;
import org.Scsp.com.model.MemberPlanSubscription;
import org.Scsp.com.service.MemberPlanSubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final MemberPlanSubscriptionService memberPlanSubscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam Long memberId, @RequestParam Long planId) {
        return ResponseEntity.ok(memberPlanSubscriptionService.subscribe(memberId, planId));
    }

    @GetMapping("/member/{memberId}")
    public List<MemberPlanSubscription> getSubscriptions(@PathVariable Long memberId) {
        return memberPlanSubscriptionService.getSubscriptions(memberId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@RequestParam Long id, @RequestParam Long memberId) {
        memberPlanSubscriptionService.cancelSubscription(id, memberId);
        return ResponseEntity.ok("Cancelled");
    }
}
