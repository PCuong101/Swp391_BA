// src/main/java/org/Scsp/com/model/SmokingHistory.java
package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserDailyLogs")
public class UserDailyLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate logDate;

    private Boolean smokedToday;

    private Integer cigarettesSmoked;

    private Integer cravingLevel;

    private Integer stressLevel;

    private String mood;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "quit_plan_id")
    private QuitPlan quitPlan;
}