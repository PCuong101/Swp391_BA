package org.Scsp.com.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AchievementDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime achievedAt;
    private boolean shared;
    private String iconUrl;
}
