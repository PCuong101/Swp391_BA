package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QuitPlans")
public class QuitPlans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanID")
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private Users user;

    @Column(name = "Reason", columnDefinition = "NVARCHAR(MAX)")
    private String reason;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "ExpectedQuitDate")
    private LocalDateTime expectedQuitDate;

    @Column(name = "CigarettesPerDay")
    private Integer cigarettesPerDay;

    @Column(name = "SmokingFrequency", columnDefinition = "NVARCHAR(50)")
    private String smokingFrequency;

    @Column(name = "AverageCost", precision = 10, scale = 2)
    private BigDecimal averageCost;


    @Column(name = "StartedSmokingAt")
    private LocalDateTime startedSmokingAt;


    private BigDecimal moneySaved = BigDecimal.ZERO;

    @Column(name = "PersonalizedNotes", columnDefinition = "NVARCHAR(MAX)")
    private String personalizedNotes;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "LastUpdated")
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HealthMilestone> milestones = new ArrayList<>();

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserDailyLogs> userDailyLogs = new ArrayList<>();

    @Column(name = "is_active")
    private Boolean isActive = true;



}

