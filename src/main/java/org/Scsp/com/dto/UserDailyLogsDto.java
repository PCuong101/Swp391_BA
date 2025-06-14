package org.Scsp.com.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.Scsp.com.model.QuitPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
public class UserDailyLogsDto {
    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime logDate = LocalDateTime.now();


    private String smokedToday;
    private Integer cigarettesSmoked;
    private Integer cravingLevel;
    private String mood;
    private Integer stressLevel;
    private String notes;
    private Integer spentMoneyOnCigarettes;

    // Add other fields as needed
}