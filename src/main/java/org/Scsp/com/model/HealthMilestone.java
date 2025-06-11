package org.Scsp.com.model;

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

    private String name;

    private String description;

    private LocalDateTime expectedDate;

    private LocalDateTime originalExpectedDate;

    private boolean achieved;

    @ManyToOne
    @JoinColumn(name = "quit_plan_id")
    private QuitPlan quitPlan;

}
