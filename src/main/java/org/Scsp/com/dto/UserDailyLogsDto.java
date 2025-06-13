package org.Scsp.com.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.Scsp.com.model.QuitPlan;

import java.time.LocalDateTime;


@Data
@Builder
public class UserDailyLogsDto {
    private Long userId;
    private LocalDateTime logDate = LocalDateTime.now();
    private Boolean smokedToday;
    private Integer cigarettesSmoked;
    private Integer cravingLevel;
    private String mood;
    private Integer stressLevel;
    private String notes;
    private Integer spentMoneyOnCigarettes;

    // Add other fields as needed
}