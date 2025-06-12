package org.Scsp.com.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Builder
public class UserDailyLogsDto {
    private Long quitPlanId;
    private LocalDateTime logDate;
    private String note;
    private int cigarettesSmoked;
    private boolean smokedToday;

    // Add other fields as needed
}