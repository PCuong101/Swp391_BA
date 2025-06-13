package org.Scsp.com.model;

import jakarta.persistence.*;

import lombok.*;

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
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coachID")
    private User coach;

    private LocalDate bookingDate;

    @ManyToOne
    @JoinColumn(name = "slotID")
    private Slot slot;

    private String status = "Booked";
    private String notes;
    private LocalDateTime createdAt = LocalDateTime.now();
}
