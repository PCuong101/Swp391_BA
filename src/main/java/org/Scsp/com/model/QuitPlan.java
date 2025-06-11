package org.Scsp.com.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "QuitPlans")
public class QuitPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgressTracking> progressTrackings;

    @OneToMany(mappedBy = "quitPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SmokingHistory> smokingHistories;

    // getters and setters
}