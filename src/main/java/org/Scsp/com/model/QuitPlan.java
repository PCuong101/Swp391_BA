package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "QuitPlans")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuitPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planID;

    @OneToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @Lob
    private String reason;

    private LocalDateTime startDate;
    private LocalDateTime expectedQuitDate;

    @Lob
    private String personalizedNotes;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    private Integer cigarettesPerDay;
    private Integer smokingFrequency;
    private Integer yearsSmoking;

    @Column(precision = 10, scale = 2)
    private BigDecimal averageCost;

    private String startedSmokingAt;
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HealthMilestone> milestones = new ArrayList<>();
    // getters/setters

    @OneToMany
    @JoinColumn(name = "planID", nullable = false)
    private List<UserDailyLog> userDailyLogs = new ArrayList<>();
}


