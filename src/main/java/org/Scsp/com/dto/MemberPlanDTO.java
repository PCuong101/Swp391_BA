package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class MemberPlanDTO {
    private Long planID;
    private String planName;
    private String description;
    private String features;
    private BigDecimal price;

}
