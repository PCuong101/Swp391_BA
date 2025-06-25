
package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CompletedTaskDTO {
    private String title;
    private String description;
    private LocalDateTime completedAt;
}
