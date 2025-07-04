package org.Scsp.com.service;

import org.Scsp.com.model.MemberPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberPlanService {
    public List<MemberPlan> getAllPlans();
}
