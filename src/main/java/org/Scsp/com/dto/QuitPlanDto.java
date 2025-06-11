package org.Scsp.com.dto;

import java.time.LocalDateTime;

public class QuitPlanDto {
    private Long userId;

    private String reason;

    private LocalDateTime startDate;

    private LocalDateTime expectedQuitDate;

    private String personalizedNotes;

    private Integer cigarettesPerDay;

    private String smokingFrequency;

    private Double averageCost;

    private LocalDateTime startedSmokingAt;


    public QuitPlanDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpectedQuitDate() {
        return expectedQuitDate;
    }

    public void setExpectedQuitDate(LocalDateTime expectedQuitDate) {
        this.expectedQuitDate = expectedQuitDate;
    }

    public String getPersonalizedNotes() {
        return personalizedNotes;
    }

    public void setPersonalizedNotes(String personalizedNotes) {
        this.personalizedNotes = personalizedNotes;
    }

    public Integer getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(Integer cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public String getSmokingFrequency() {
        return smokingFrequency;
    }

    public void setSmokingFrequency(String smokingFrequency) {
        this.smokingFrequency = smokingFrequency;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

    public LocalDateTime getStartedSmokingAt() {
        return startedSmokingAt;
    }

    public void setStartedSmokingAt(LocalDateTime startedSmokingAt) {
        this.startedSmokingAt = startedSmokingAt;
    }
}
