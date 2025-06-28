package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRegisterDTO {
    private String smokeType;
    private Integer cigarettesPerDay;
    private Integer yearsSmoking;
    private Integer packPrice;
    private String firstSmokeTime;
    private String dateStart;
    private String username;
    private String email;
    private String password;

}
