package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.filter.FlightFilterDTO;
import com.demo.flight_booking.mapper.FlightMapper;
import com.demo.flight_booking.model.Flight;
import com.demo.flight_booking.repository.FlightRepository;
import com.demo.flight_booking.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO create(FlightDTO dto) {
        Flight flight = flightMapper.toEntity(dto);
        Flight saved = flightRepository.save(flight);
        return flightMapper.toDTO(saved);
    }

    @Override
    public FlightDTO update(Long id, FlightDTO dto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setBasePrice(dto.getBasePrice());

        Flight updated = flightRepository.save(flight);
        return flightMapper.toDTO(updated);
    }

    @Override
    public FlightDTO getById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return flightMapper.toDTO(flight);
    }

    @Override
    public List<FlightDTO> getAll() {
        return flightRepository.findAll().stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Flight not found with id: " + id);
        }

        flightRepository.deleteById(id);
    }

    @Override
    public List<FlightDTO> searchFlights(FlightFilterDTO filter) {
        LocalDateTime depStart = filter.getDepartureStartTime() != null
                ? filter.getDepartureStartTime()
                : LocalDateTime.of(1970, 1, 1, 0, 0);

        LocalDateTime depEnd = filter.getDepartureEndTime() != null
                ? filter.getDepartureEndTime()
                : LocalDateTime.of(9999, 12, 31, 23, 59);

        List<Flight> flights = flightRepository.filterFlights(
                filter.getDepartureCity(),
                filter.getArrivalCity(),
                filter.getAirlineName(),
                filter.getMaxPrice(),
                depStart,
                depEnd
        );

        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }
}
