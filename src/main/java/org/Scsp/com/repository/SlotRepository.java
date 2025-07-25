package org.Scsp.com.repository;

import org.Scsp.com.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}