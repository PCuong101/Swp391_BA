package org.Scsp.com.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;


@Data
@Builder
@AllArgsConstructor
public class MilestoneTemplate {
    private String name;
    private String description;
    private Duration offset; // ví dụ: 20 phút, 8 giờ, 2 ngày


}
