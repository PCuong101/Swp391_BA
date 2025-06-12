package org.Scsp.com.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MemberPlanSubscriptionId implements Serializable {
    private Long userID;
    private Long planID;

    // equals() and hashCode() implementations
}