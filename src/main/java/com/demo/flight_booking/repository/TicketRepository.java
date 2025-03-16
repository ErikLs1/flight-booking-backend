package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations for Ticket entities.
 * <p>
 *     Provides a custom method to retrieve tickets by passengers email.
 * </p>
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Finds all ticket entities associated with a person by the given email.
     * @param email the email of the person.
     * @return a list of Ticket entities fo the specified email
     */
    List<Ticket> findByPerson_Email(String email);
}
