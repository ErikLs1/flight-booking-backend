package com.demo.flight_booking.dto;

import com.demo.flight_booking.model.enums.PaymentMethod;
import com.demo.flight_booking.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long paymentId;
    private Long bookingId;
    private Double amount;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
}
