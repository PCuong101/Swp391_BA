package org.Scsp.com.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TemplateID", nullable = false)
    private AchievementTemplate achievementTemplate;

    @Column(name = "DateAchieved")
    private LocalDateTime dateAchieved;

    @Column(name = "Shared")
    private Boolean shared = false;

    @Column(name = "CreatedAt")
    private Date createdAt;
}
