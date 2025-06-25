package org.Scsp.com.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberPlanSubscriptionId implements Serializable {
    private Long userID;
    private Long planID;

    // equals() and hashCode() implementations
}