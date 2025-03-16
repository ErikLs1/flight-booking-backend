package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.SeatClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing basic CRUD operations on SeatClass entities.
 */
@Repository
public interface SeatClassRepository extends JpaRepository<SeatClass, Long> {
}
