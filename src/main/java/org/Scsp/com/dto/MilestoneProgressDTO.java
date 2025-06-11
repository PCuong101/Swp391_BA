package org.Scsp.com.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class MilestoneProgressDTO {
    String name;
    LocalDateTime expectedDate;
    int progressPercent;
    boolean achieved;
}
