// src/main/java/org/Scsp/com/model/ProgressTracking.java
package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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
    private int progressId;

    @ManyToOne
    @JoinColumn(name = "PlanID", nullable = false)
    private QuitPlans quitPlans;

    @Column(name = "DaysSmokeFree")
    private int daysSmokeFree;

    @Column(name = "MoneySaved", precision = 10, scale = 2)
    private BigDecimal moneySaved;

    @Column(name = "PulseRate")
    private int pulseRate;

    @Column(name = "OxygenLevel")
    private int oxygenLevel;

    @Column(name = "TasteAndSmell")
    private int tasteAndSmell;

    @Column(name = "NicotineFromBody")
    private int nicotineFromBody;

    @Column(name = "Breathing")
    private int breathing;

    @Column(name = "DateUpdated")
    private Date dateUpdated;


}