package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class TaskCompletionStatsDTO {
    private Long userId;
    private int totalTasks;
    private int completedTasks;
    private double completionPercentage;

}
