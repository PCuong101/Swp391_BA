package org.Scsp.com.dto;

import java.util.Date;

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

    public SurveyRegisterDTO(String smokeType, int cigarettesPerDay, int yearsSmoking, int packPrice, String firstSmokeTime, Date dateStart, String username, String email, String password) {
        this.smokeType = smokeType;
        this.cigarettesPerDay = cigarettesPerDay;
        this.yearsSmoking = yearsSmoking;
        this.packPrice = packPrice;
        this.firstSmokeTime = firstSmokeTime;
        this.dateStart = dateStart;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public SurveyRegisterDTO() {
    }

    public String getSmokeType() {
        return smokeType;
    }

    public void setSmokeType(String smokeType) {
        this.smokeType = smokeType;
    }

    public int getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(int cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public int getYearsSmoking() {
        return yearsSmoking;
    }

    public void setYearsSmoking(int yearsSmoking) {
        this.yearsSmoking = yearsSmoking;
    }

    public int getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(int packPrice) {
        this.packPrice = packPrice;
    }

    public String getFirstSmokeTime() {
        return firstSmokeTime;
    }

    public void setFirstSmokeTime(String firstSmokeTime) {
        this.firstSmokeTime = firstSmokeTime;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SurveyRegisterDTO{" +
                "smokeType='" + smokeType + '\'' +
                ", cigarettesPerDay=" + cigarettesPerDay +
                ", yearsSmoking=" + yearsSmoking +
                ", packPrice=" + packPrice +
                ", firstSmokeTime='" + firstSmokeTime + '\'' +
                ", dateStart=" + dateStart +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
