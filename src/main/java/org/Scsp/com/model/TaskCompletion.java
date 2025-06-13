package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TaskCompletions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completionID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "templateID")
    private TaskTemplate template;

    private LocalDateTime completedAt = LocalDateTime.now();
}

