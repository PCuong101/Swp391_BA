package org.Scsp.com.repository;

import org.Scsp.com.model.MemberPlanSubscription;
import org.Scsp.com.model.MemberPlanSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberPlanSubscriptionRepository extends JpaRepository<MemberPlanSubscription, MemberPlanSubscriptionId> {
    List<MemberPlanSubscription> findById_UserID(Long idUserID);
}
