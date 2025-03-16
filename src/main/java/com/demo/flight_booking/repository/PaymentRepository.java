package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to perform basic CRUD operations on Payment entities.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
