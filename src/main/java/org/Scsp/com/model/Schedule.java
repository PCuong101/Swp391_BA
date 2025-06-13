package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    @JoinColumn(name = "coachID")
    private User coach;

    @ManyToOne
    @JoinColumn(name = "slotID")
    private Slot slot;

    private LocalDate date;
    private Integer isAvailable;
}