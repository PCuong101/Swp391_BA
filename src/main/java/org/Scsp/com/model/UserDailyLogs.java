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
    private Long id;

    private LocalDateTime logDate;

    private Boolean smokedToday;

    private Integer cigarettesSmoked;

    private Integer cravingLevel;

    private Integer stressLevel;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String mood;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "quit_plan_id")
    @JsonBackReference
    private QuitPlan quitPlan;
}