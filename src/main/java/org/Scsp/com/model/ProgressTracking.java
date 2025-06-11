// src/main/java/org/Scsp/com/model/ProgressTracking.java
package org.Scsp.com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ProgressTracking")
public class ProgressTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quit_plan_id")
    private QuitPlan quitPlan;



    // other fields, getters and setters
}