package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneProgressDTO {
    String name;
    int progressPercent;
    boolean achieved;
    LocalDateTime recoveryEndTime;
}
