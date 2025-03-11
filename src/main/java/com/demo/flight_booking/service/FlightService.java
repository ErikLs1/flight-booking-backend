package com.demo.flight_booking.service;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.SeatClassFeeDto;
import com.demo.flight_booking.dto.filter.FlightFilterDTO;
import com.demo.flight_booking.model.enums.SeatClassType;

import java.util.List;

public interface FlightService extends BasicService<FlightDTO, Long> {
    List<FlightDTO> searchFlights(FlightFilterDTO filter);
    String getAircraftModelByFlightId(Long flightId);
    List<SeatClassType> getSeatClassesByFlightId(Long flightId);
    List<String> getFlightCitiesByFlightId(Long flightId);
    List<SeatClassFeeDto> getSeatClassesForFlight(Long flightId);
}
