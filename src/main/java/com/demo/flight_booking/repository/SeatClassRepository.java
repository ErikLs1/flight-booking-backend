package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.SeatClass;
import com.demo.flight_booking.model.enums.SeatClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatClassRepository extends JpaRepository<SeatClass, Long> {
    Optional<SeatClass> findBySeatClassName(SeatClassType seatClassName);

}
