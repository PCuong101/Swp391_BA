package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRegisterDTO {
    private String smokeType;
    private int cigarettesPerDay;
    private int yearsSmoking;
    private int packPrice;
    private String firstSmokeTime;
    private Date dateStart;
    private String username;
    private String email;
    private String password;

}
