package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class ScheduleOverviewDTO {
    private Long scheduleId;
    private LocalDate date;
    private String slotLabel;
    private String availableLabel;

    public String getAvailableLabel() {
        return availableLabel;
    }

    public void setAvailableLabel(String availableLabel) {
        this.availableLabel = availableLabel;
    }

    // Nếu đã book thì có thông tin user
    private String bookedByName;
    private String bookedByEmail;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSlotLabel() {
        return slotLabel;
    }

    public void setSlotLabel(String slotLabel) {
        this.slotLabel = slotLabel;
    }



    public String getBookedByName() {
        return bookedByName;
    }

    public void setBookedByName(String bookedByName) {
        this.bookedByName = bookedByName;
    }

    public String getBookedByEmail() {
        return bookedByEmail;
    }

    public void setBookedByEmail(String bookedByEmail) {
        this.bookedByEmail = bookedByEmail;
    }

// Constructor, getters, setters...
}
