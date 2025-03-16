package com.demo.flight_booking.model;

import com.demo.flight_booking.model.enums.PaymentMethod;
import com.demo.flight_booking.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a payment made for a booking.
 *
 * <p>
 *     Stores information about the total amount for a booking.
 *     It is linked to Booking entity.
 * </p>
 */
@Entity
@Table(name = "PAYMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    /**
     * The booking associated with the payment.
     */
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    /**
     * The total amount paid.
     */
    @Column(nullable = false)
    private Double amount;

    /**
     * The status of the payment (e.g., COMPLETED, CANCELLED).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    /**
     * The payment method used (e.g, CREDIT_CARD, PAYPAL).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod paymentMethod;
}
