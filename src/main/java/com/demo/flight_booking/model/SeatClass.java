package com.demo.flight_booking.model;

import com.demo.flight_booking.model.enums.SeatClassType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a seat class.
 * <p>
 *     Stores information of seat class and its fee.
 *     It is linked with Aircraft and Seat.
 * </p>
 */
@Entity
@Table(name = "SEAT_CLASS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatClassId;

    /**
     * The aircraft associated with this seat class.
     */
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    /**
     * The seat class type (e.g., ECONOMY, PREMIUM).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeatClassType seatClassName;

    /**
     * The additional fee associated with this seat class.
     */
    @Column(nullable = false)
    private Double basePrice;

    /**
     * The list of seats that belong to this seat class.
     */
    @OneToMany(mappedBy = "seatClass", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
