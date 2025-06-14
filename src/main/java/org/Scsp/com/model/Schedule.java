package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulesID;

    @ManyToOne @JoinColumn(name = "CoachID", nullable = false)
    private User coach;

    @ManyToOne @JoinColumn(name = "SlotID", nullable = false)
    private Slot slot;

    @OneToMany(mappedBy = "schedule") private List<Booking> bookings;

    private LocalDate date;
    private boolean isAvailable;
}