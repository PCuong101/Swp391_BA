// src/main/java/org/Scsp/com/model/SmokingHistory.java
package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "UserDailyLogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDailyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logID;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "planID", nullable = false)
    private QuitPlan quitPlan;

    private LocalDateTime logDate = LocalDateTime.now();
    private Boolean smokedToday;
    private Integer cigarettesSmoked;
    private Integer cravingLevel;
    private String mood;
    private Integer stressLevel;
    private String notes;
    private Integer spentMoneyOnCigarettes;
}