package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachDTO {
    Long id;
    String name;
    String email;
    String avatar;
    List<ScheduleDTO> schedules;

}
