package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MemberPlanSubscription")
@Getter
@Setter
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

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String notes;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
}
