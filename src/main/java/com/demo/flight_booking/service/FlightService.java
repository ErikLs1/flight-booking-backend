package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.filter.FlightFilterDTO;

import java.util.List;

public interface FlightService extends BasicService<FlightDTO, Long> {
    public List<FlightDTO> searchFlights(FlightFilterDTO filter);
}
