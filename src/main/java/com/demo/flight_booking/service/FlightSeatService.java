package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.FlightSeatDTO;

import java.util.List;

public interface FlightSeatService extends BasicService<FlightSeatDTO, Long> {
    List<FlightSeatDTO> getSeatByFlightId(Long flightId);
}
