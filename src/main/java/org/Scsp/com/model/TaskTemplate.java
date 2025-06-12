package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TaskTemplates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateID;

    private String title;
    private String description;
    private Integer suggestedDay;
    private String addictionLevel;
}