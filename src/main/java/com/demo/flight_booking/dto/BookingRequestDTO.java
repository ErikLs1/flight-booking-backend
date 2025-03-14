package com.demo.flight_booking.dto;

import com.demo.flight_booking.model.enums.PaymentMethod;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequestDTO {
    private Long flightId;
    private List<PersonDTO> passengers;
    private List<Long> seatIds;
    private PaymentMethod paymentMethod;
}
