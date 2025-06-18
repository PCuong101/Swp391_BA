package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Scsp.com.Enum.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private LocalDate bookingDate;
    private String meetingLink;
    private String note;
    private BookingStatus status;
    private LocalTime startTime;
    private LocalTime endTime;
    private String coachName;
}
