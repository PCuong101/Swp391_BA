package org.Scsp.com.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AchievementID")
    private Long achievementId;


    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TemplateID", nullable = false)
    private AchievementTemplate template;

    @Column(name = "DateAchieved")
    private LocalDateTime dateAchieved;

    @Column(name = "Shared")
    private Boolean shared = false;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
}
