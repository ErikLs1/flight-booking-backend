package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to perform basic CRUD operations on Booking entities.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
