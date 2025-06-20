package org.Scsp.com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulesID;

    @ManyToOne
    @JoinColumn(name = "CoachID", nullable = false)
    @JsonIgnoreProperties({"schedules", "bookings", "quitPlans", "password", "email"})
    private User coach;

    @ManyToOne
    @JoinColumn(name = "SlotID", nullable = false)
    private Slot slot;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("schedule") // tránh booking → schedule → booking...
    private List<Booking> bookings;

    private LocalDate date;
    private boolean isAvailable;
}
