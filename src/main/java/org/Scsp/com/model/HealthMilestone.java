package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@Table(name = "HealthMilestones")
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

    @Column(nullable = false)
    private double weight; // Weight of the milestone

    private boolean achieved = false;

    @ManyToOne
    @JoinColumn(name = "planID")
    @JsonBackReference
    private QuitPlan quitPlan;

}
