// src/main/java/org/Scsp/com/model/SmokingHistory.java
package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserDailyLogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDailyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logID;

    @ManyToOne
    @JoinColumn(name = "planID")
    private QuitPlan quitPlan;

    private LocalDateTime logDate = LocalDateTime.now();
    private Boolean smokedToday;
    private Integer cigarettesSmoked;
    private Integer cravingLevel;
    private String mood;
    private Integer stressLevel;
    private String notes;
}