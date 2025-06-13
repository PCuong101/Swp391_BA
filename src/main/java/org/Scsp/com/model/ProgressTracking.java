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
@Table(name = "ProgressTracking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progressID;

    @ManyToOne
    @JoinColumn(name = "planID", nullable = false)
    private QuitPlan plan;

    private Integer daysSmokeFree;

    @Column(precision = 10, scale = 2)
    private BigDecimal moneySaved;

    private Integer pulseRate;
    private Integer oxygenLevel;
    private Integer tasteAndSmell;
    private Integer nicotineFromBody;
    private Integer breathing;

    private LocalDateTime dateUpdated = LocalDateTime.now();


}
