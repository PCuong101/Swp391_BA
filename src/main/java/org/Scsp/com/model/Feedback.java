package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    private Integer rating;
    private String comments;
    private LocalDateTime createdAt = LocalDateTime.now();
}