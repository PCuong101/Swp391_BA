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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserDailyLogs")
public class UserDailyLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogID")
    private Long logId;

    @Column(name = "LogDate")
    private LocalDateTime logDate;

    @Column(name = "SmokedToday", nullable = false)
    private Boolean smokedToday;

    @Column(name = "CigarettesSmoked")
    private Integer cigarettesSmoked;

    @Column(name = "CravingLevel")
    private Integer cravingLevel;

    @Column(name = "StressLevel")
    private Integer stressLevel;

    @Column(name = "Mood", columnDefinition = "NVARCHAR(50)")
    private String mood;

    @Column(name = "Notes", columnDefinition = "NVARCHAR(MAX)")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "quit_plan_id")
    @JsonBackReference
    private QuitPlan quitPlan;
}