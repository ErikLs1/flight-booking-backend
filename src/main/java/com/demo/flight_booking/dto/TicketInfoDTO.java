package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfoDTO {
    private Long ticketId;
    private Double ticketPrice;
    private String seatNumber;
    private String seatClass;
    private PersonDTO person;
    private FlightInfoDTO flight;
}
