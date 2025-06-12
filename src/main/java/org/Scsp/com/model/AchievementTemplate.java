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
    @Column(name = "TemplateID")
    private Integer templateId;

    @Column(name = "Title", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String title;

    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "Category", columnDefinition = "NVARCHAR(100)")
    private String category;

    @Column(name = "CustomLogicKey", columnDefinition = "NVARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private CustomLogicKey customLogicKey;

    @Column(name = "Visible")
    private Boolean visible = true;
}