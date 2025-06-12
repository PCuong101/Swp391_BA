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
@Table(name = "health_milestones")
@NoArgsConstructor
public class HealthMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String description;

    private LocalDateTime expectedDate;

    private LocalDateTime originalExpectedDate;

    private boolean achieved = false;

    @ManyToOne
    @JoinColumn(name = "quit_plan_id")
    @JsonBackReference
    private QuitPlan quitPlan;

}
