package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.SeatClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatClassRepository extends JpaRepository<SeatClass, Long> {
}
