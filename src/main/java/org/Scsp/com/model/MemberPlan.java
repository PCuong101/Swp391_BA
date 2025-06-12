package org.Scsp.com.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "MemberPlans")
public class MemberPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planID;

    @Column(nullable = false, length = 100)
    private String planName;

    @Lob
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Lob
    private String features;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // getters/setters
}
