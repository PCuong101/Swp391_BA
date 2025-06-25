package org.Scsp.com.service.impl;

import lombok.RequiredArgsConstructor;
import org.Scsp.com.Enum.MemberPlanSubscriptionStatus;
import org.Scsp.com.Enum.Role;
import org.Scsp.com.model.MemberPlan;
import org.Scsp.com.model.MemberPlanSubscription;
import org.Scsp.com.model.MemberPlanSubscriptionId;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.MemberPlanRepository;
import org.Scsp.com.repository.MemberPlanSubscriptionRepository;
import org.Scsp.com.service.MemberPlanSubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPlanSubscriptionServiceImpl implements MemberPlanSubscriptionService {

    private final MemberPlanSubscriptionRepository memberPlanSubscriptionRepository;
    private final MemberPlanRepository memberPlanRepository;
    private final UsersServiceImpl usersService;
    @Override
    public MemberPlanSubscription subscribe(Long memberId, Long planId) {
        User user = usersService.getUserById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + memberId));
        MemberPlan memberPlan = memberPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found with id: " + planId));
        MemberPlanSubscriptionId memberPlanSubscriptionId = new MemberPlanSubscriptionId(user.getUserId(),memberPlan.getPlanID());
        user.setRole(Role.MEMBER_VIP1);

        MemberPlanSubscription subscription = MemberPlanSubscription.builder()
                .id(memberPlanSubscriptionId)
                .user(user)
                .status(MemberPlanSubscriptionStatus.ACTIVE)
                .plan(memberPlan)
                .build();

        return memberPlanSubscriptionRepository.save(subscription);
    }

    @Override
    public List<MemberPlanSubscription> getSubscriptions(Long memberId) {
        return  memberPlanSubscriptionRepository.findById_UserID(memberId);
    }

    @Override
    public void cancelSubscription(Long userId, Long memberId) {
        MemberPlanSubscriptionId subscriptionId = new MemberPlanSubscriptionId(userId, memberId);
        MemberPlanSubscription sub = memberPlanSubscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        sub.setStatus(MemberPlanSubscriptionStatus.CANCELLED);

        memberPlanSubscriptionRepository.save(sub);
    }
}
