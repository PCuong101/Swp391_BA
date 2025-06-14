package org.Scsp.com.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Achievements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementID;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "templateID")
    private AchievementTemplate achievementTemplate;

    private LocalDateTime dateAchieved = LocalDateTime.now();
    private Boolean shared = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}

