// src/main/java/org/Scsp/com/model/SmokingHistory.java
package org.Scsp.com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SmokingHistory")
public class SmokingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quit_plan_id")
    private QuitPlan quitPlan;

    // other fields, getters and setters
}