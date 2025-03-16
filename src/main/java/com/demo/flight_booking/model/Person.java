package com.demo.flight_booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a person.
 *
 * <p>
 *     Stores personal information like first name, last name, email and phone number.
 *     It is linked to Ticket entity.
 * </p>
 */
@Entity
@Table(name = "PERSON")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    /**
     * The first name of the person.
     */
    @Column(length = 50, nullable = false)
    private String firstName;

    /**
     * The last name of the person.
     */
    @Column(length = 50, nullable = false)
    private String lastName;

    /**
     * The email of the person.
     */
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    /**
     * The phone of the person.
     */
    @Column(length = 20)
    private String phone;

    /**
     * The list of tickets associated with the person.
     */
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
