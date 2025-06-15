package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Scsp.com.Enum.CustomLogicKey;

@Entity
@Table(name = "AchievementTemplates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateID;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String title;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String description;
    private String category;
    @Enumerated(EnumType.STRING)
    private CustomLogicKey customLogicKey;
    private Boolean visible = true;
}