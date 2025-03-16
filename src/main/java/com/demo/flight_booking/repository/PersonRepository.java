package com.demo.flight_booking.repository;

import com.demo.flight_booking.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operation of Person entities.
 *
 * <p>
 *     Provides a custom method to fin a person by email.
 * </p>
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    /**
     * Finds a person entity by its email.
     *
     * @param email the email address.
     * @return the Person entity with the specified email, or null if not found.
     */
    Person findByEmail(String email);
}
