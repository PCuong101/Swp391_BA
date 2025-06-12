package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuitPlanDto {
    private Long userId;

    private String reason;

    private LocalDateTime startDate;

    private LocalDateTime expectedQuitDate;

    private String personalizedNotes;

    private Integer cigarettesPerDay;

    private String smokingFrequency;

    private BigDecimal averageCost;

    private LocalDateTime startedSmokingAt;


}
