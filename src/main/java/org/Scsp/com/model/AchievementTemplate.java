package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.*;
import org.Scsp.com.Enum.CustomLogicKey;

@Entity
@Table(name = "AchievementTemplates")
@Getter
@Setter
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
    private Integer threshold;
    private String iconUrl;
    private Boolean visible = true;
}