package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Scsp.com.Enum.CustomLogicKey;

import java.util.List;

@Entity
@Table(name = "AchievementTemplates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String title;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String description;
    private String category;

    @Enumerated(EnumType.STRING)
    private CustomLogicKey customLogicKey;

    private boolean visible = true;

    @OneToMany(mappedBy = "template")
    private List<Achievement> achievements;
}