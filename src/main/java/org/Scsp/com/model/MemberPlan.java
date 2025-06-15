package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "MemberPlans")
public class MemberPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planID;

    @Column(nullable = false, length = 100)
    private String planName;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String features;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // getters/setters
}
