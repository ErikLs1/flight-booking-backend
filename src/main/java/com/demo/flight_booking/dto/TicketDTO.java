package com.demo.flight_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long ticketId;
    private Long personId;
    private Long flightSeatId;
    private Double ticketPrice;
    private LocalDateTime bookingDate;
}
