package org.Scsp.com.dto;

import lombok.Getter;
import lombok.Setter;
import org.Scsp.com.Enum.AddictionLevel;
@Getter
@Setter
public class TaskDTO {
     private long templateID;
     private String title;
        private String description;
        private Integer suggestedDay;
    private AddictionLevel addictionLevel;
    private boolean completed;
}
