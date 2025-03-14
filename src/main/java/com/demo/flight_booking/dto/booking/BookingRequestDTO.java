package com.demo.flight_booking.dto.booking;

import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.model.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
    private Long flightId;
    private List<PersonDTO> passengers;
    private List<Long> seatIds;
    private PaymentMethod paymentMethod;
}
