package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ScheduleID", nullable = false)
    @JsonIgnore
    private Schedule schedule;


    @Column(name = "BookingDate")
    private LocalDate bookingDate = LocalDate.now();

    @Column(name = "Status")
    private String status = "Booked";

    @Column(name = "Notes")
    private String notes;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    @PrePersist
    protected void onCreate() {
        if (bookingDate == null) bookingDate = LocalDate.now();
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    // Nếu cần tránh vòng lặp JSON, bạn có thể:
    // @JsonIgnore
    // private Schedule schedule;
}
