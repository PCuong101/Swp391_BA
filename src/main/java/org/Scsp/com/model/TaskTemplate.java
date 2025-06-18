package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Scsp.com.Enum.AddictionLevel;

@Entity
@Table(name = "TaskTemplates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateID;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String title;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String description;

    private Integer suggestedDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "addiction_level")
    private AddictionLevel addictionLevel;
}
