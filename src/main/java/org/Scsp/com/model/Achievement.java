package org.Scsp.com.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "templateID")
    private AchievementTemplate achievementTemplate;

    private LocalDateTime dateAchieved = LocalDateTime.now();
    private Boolean shared = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}

