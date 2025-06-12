package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "QuitPlans")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuitPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planID;

    @ManyToOne
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
    private String smokingFrequency;

    @Column(precision = 10, scale = 2)
    private BigDecimal averageCost;

    private LocalDateTime startedSmokingAt;
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HealthMilestone> milestones = new ArrayList<>();
    // getters/setters
}


