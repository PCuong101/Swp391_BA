package org.Scsp.com.repository;

import org.Scsp.com.Enum.MemberPlanSubscriptionStatus;
import org.Scsp.com.model.MemberPlanSubscription;
import org.Scsp.com.model.MemberPlanSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface MemberPlanSubscriptionRepository extends JpaRepository<MemberPlanSubscription, MemberPlanSubscriptionId> {
    List<MemberPlanSubscription> findById_UserID(Long idUserID);

    @Query("""
                SELECT SUM(p.plan.price)
                FROM MemberPlanSubscription p
                WHERE p.status = :status
                  AND p.createdAt BETWEEN :start AND :end
            """)
    BigDecimal getRevenueBetween(
            @Param("status") MemberPlanSubscriptionStatus status,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
