package org.Scsp.com.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MemberPlanSubscription")
public class MemberPlanSubscription {
    @EmbeddedId
    private MemberPlanSubscriptionId id;

    @ManyToOne
    @MapsId("userID")
    private User user;

    @ManyToOne
    @MapsId("planID")
    private MemberPlan plan;

    private String status = "Pending";

    @Lob
    private String notes;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
}
