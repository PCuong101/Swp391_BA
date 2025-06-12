// src/main/java/org/Scsp/com/model/ProgressTracking.java
package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "ProgressTracking")
@NoArgsConstructor
public class ProgressTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgressID")
    private Integer progressId;

    @ManyToOne
    @JoinColumn(name = "PlanID", nullable = false)
    private QuitPlan quitPlan;

    @Column(name = "DaysSmokeFree")
    private Integer daysSmokeFree;

    @Column(name = "MoneySaved", precision = 10, scale = 2)
    private BigDecimal moneySaved;

    @Column(name = "PulseRate")
    private Integer pulseRate;

    @Column(name = "OxygenLevel")
    private Integer oxygenLevel;

    @Column(name = "TasteAndSmell")
    private Integer tasteAndSmell;

    @Column(name = "NicotineFromBody")
    private Integer nicotineFromBody;

    @Column(name = "Breathing")
    private Integer breathing;

    @Column(name = "DateUpdated")
    private LocalDateTime dateUpdated;


}