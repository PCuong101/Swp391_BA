package org.Scsp.com.service;

import org.Scsp.com.model.MemberPlanSubscription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberPlanSubscriptionService {
    MemberPlanSubscription subscribe(Long memberId, Long planId);
    List<MemberPlanSubscription> getSubscriptions(Long memberId);
    void cancelSubscription(Long userId, Long memberId);
}
