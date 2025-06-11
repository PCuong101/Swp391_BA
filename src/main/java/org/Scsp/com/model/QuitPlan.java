package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "QuitPlans")
public class QuitPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String reason;

    private LocalDateTime startDate;
    private LocalDateTime expectedQuitDate;

    private Integer cigarettesPerDay;
    private String smokingFrequency;
    private Double averageCost;
    private LocalDateTime startedSmokingAt;

    private String personalizedNotes;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HealthMilestone> milestones = new ArrayList<>();

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserDailyLogs> userDailyLogs = new ArrayList<>();

    @Column(name = "is_active")
    private Boolean isActive = true;

    public QuitPlan() {
        // Default constructor
    }

    // Getters, Setters
}

