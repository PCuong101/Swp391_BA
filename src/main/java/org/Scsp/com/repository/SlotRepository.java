package org.Scsp.com.repository;

import org.Scsp.com.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}