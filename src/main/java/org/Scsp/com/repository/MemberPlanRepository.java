package org.Scsp.com.repository;

import org.Scsp.com.model.MemberPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPlanRepository extends JpaRepository<MemberPlan, Long> {
    MemberPlan findByPlanID(long planId);
}
