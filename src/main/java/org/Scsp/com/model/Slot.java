package org.Scsp.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalTime;


@Entity
@Table(name = "Slot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotID;

    private LocalTime startTime;
    private LocalTime endTime;
    @Column(columnDefinition = "NVARCHAR(100)")
    private String label;
    @OneToMany(mappedBy = "slot") private List<Schedule> schedules;
}