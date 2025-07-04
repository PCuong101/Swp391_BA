package org.Scsp.com.service.impl;

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
}
