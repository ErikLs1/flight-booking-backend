package com.demo.flight_booking.dto.booking;

import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotNull(message = "Flight is required")
    private Long flightId;

    @NotEmpty(message = "Passenger is required")
    private List<PersonDTO> passengers;

    @NotEmpty(message = "Seat id is required")
    private List<Long> seatIds;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}
