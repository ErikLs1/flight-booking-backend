package com.demo.flight_booking.dto;

import lombok.Data;

@Data
public class TicketInfoDTO {
    private Long ticketId;
    private Double ticketPrice;
    private String seatNumber;
    private String seatClass;
    private PersonDTO person;
    private FlightInfoDTO flight;
}
