package org.Scsp.com.repository;

import org.Scsp.com.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Có thể thêm query nếu cần
}
