package org.Scsp.com.service.impl;

import org.Scsp.com.dto.MemberPlanDTO;
import org.Scsp.com.model.MemberPlan;
import org.Scsp.com.repository.MemberPlanRepository;
import org.Scsp.com.service.MemberPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberPlanServiceImpl implements MemberPlanService {

    @Autowired
    private MemberPlanRepository memberPlanRepository;

    @Override
    public List<MemberPlan> getAllPlans() {
        return memberPlanRepository.findAll();
    }

    @Override
    public MemberPlan updateMemberPlan(MemberPlanDTO memberPlan) {
        MemberPlan existingPlan = memberPlanRepository.findById(memberPlan.getPlanID())
                .orElseThrow(() -> new RuntimeException("Member plan not found with id: " + memberPlan.getPlanID()));

        existingPlan.setPlanName(memberPlan.getPlanName());
        existingPlan.setDescription(memberPlan.getDescription());
        existingPlan.setPrice(memberPlan.getPrice());
        existingPlan.setFeatures(memberPlan.getFeatures());

        return memberPlanRepository.save(existingPlan);
    }
}
