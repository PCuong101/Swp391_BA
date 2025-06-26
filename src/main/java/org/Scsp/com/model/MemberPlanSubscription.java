package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.*;
import org.Scsp.com.Enum.MemberPlanSubscriptionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "MemberPlanSubscription")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPlanSubscription {
    @EmbeddedId
    private MemberPlanSubscriptionId id;

    @ManyToOne
    @MapsId("userID")
    private User user;

    @ManyToOne
    @MapsId("planID")
    private MemberPlan plan;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MemberPlanSubscriptionStatus status;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String notes;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();


}
