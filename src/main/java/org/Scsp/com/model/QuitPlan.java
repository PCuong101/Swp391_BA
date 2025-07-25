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

    @Column(columnDefinition = "NVARCHAR(100)")
    private String reason;

    private LocalDateTime startDate;
    private LocalDateTime expectedQuitDate;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String personalizedNotes;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    private Integer cigarettesPerDay;
    private Integer yearsSmoking;

    @Column(precision = 10, scale = 2)
    private BigDecimal averageCost;

    private LocalDateTime lastUpdated = LocalDateTime.now();

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<HealthMilestone> milestones = new ArrayList<>();
    // getters/setters

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserDailyLog> userDailyLogs = new ArrayList<>();
}


