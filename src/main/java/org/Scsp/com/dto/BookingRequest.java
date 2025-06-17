package org.Scsp.com.dto;

import lombok.Data;

@Data
public class BookingRequest {
    public Long userId;
    public Long scheduleId;
    public String note;
}
