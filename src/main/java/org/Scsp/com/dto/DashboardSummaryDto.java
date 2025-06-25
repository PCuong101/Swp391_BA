package org.Scsp.com.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardSummaryDto {
    private long totalUsers;
    private long totalBlogs;
    private long activeUsers;
    private BigDecimal monthlyRevenue;
}
