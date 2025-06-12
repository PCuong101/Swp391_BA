package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Schedules")
@Data
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