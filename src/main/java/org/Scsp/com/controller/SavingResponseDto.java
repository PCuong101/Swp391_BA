package org.Scsp.com.controller;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class SavingResponseDto {
    private BigDecimal totalSavings;
    private BigDecimal totalSpentOnCigarettes;
    private BigDecimal totalSpentOnNrt;
    private BigDecimal moneyPerDay;
    private BigDecimal moneyPerWeek;
    private BigDecimal moneyPerMonth;
    private BigDecimal moneyPerYear;
}
