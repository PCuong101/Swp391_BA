package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Slot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotID;

    private java.time.LocalTime startTime;
    private java.time.LocalTime endTime;
    private String label;
    @OneToMany(mappedBy = "slot") private List<Schedule> schedules;
}