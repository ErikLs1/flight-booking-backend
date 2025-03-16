package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.FlightSeatDTO;
import com.demo.flight_booking.mapper.FlightSeatMapper;
import com.demo.flight_booking.model.FlightSeat;
import com.demo.flight_booking.repository.FlightSeatRepository;
import com.demo.flight_booking.service.FlightSeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements FlightSeat Service interface.
 *
 * <p>
 *     Provide basic CRUD operations and additional method to retrieve flight seats by flight id.
 * </p>
 */
@Service
@AllArgsConstructor
public class FlightSeatServiceImpl implements FlightSeatService {

    private final FlightSeatRepository flightSeatRepository;
    private final FlightSeatMapper flightSeatMapper;

    @Override
    public FlightSeatDTO create(FlightSeatDTO dto) {
        FlightSeat flightSeat = flightSeatMapper.toEntity(dto);
        FlightSeat saved = flightSeatRepository.save(flightSeat);
        return flightSeatMapper.toDTO(saved);
    }

    @Override
    public FlightSeatDTO update(Long id, FlightSeatDTO dto) {
        FlightSeat flightSeat =  flightSeatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FlightSeat not found id: " + id));
        flightSeat.setIsBooked(dto.getIsBooked());

        FlightSeat updated = flightSeatRepository.save(flightSeat);
        return flightSeatMapper.toDTO(updated);
    }

    @Override
    public FlightSeatDTO getById(Long id) {
        FlightSeat flightSeat = flightSeatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FlightSeat not found id: " + id));
        return flightSeatMapper.toDTO(flightSeat);
    }

    @Override
    public List<FlightSeatDTO> getAll() {
        return flightSeatRepository.findAll().stream()
                .map(flightSeatMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!flightSeatRepository.existsById(id)) {
            throw new RuntimeException("FlightSeat not found id: " + id);
        }
        flightSeatRepository.deleteById(id);
    }

    /**
     * Retrieves all flight seats for the specified flight.
     *
     * @param flightId the id of the flight.
     * @return a list of FlightSeatDTO objects for the given flight.
     */
    @Override
    public List<FlightSeatDTO> getSeatByFlightId(Long flightId) {

        return flightSeatRepository.findByFlight_FlightId(flightId).stream()
                .map(flightSeatMapper::toDTO)
                .toList();
    }
}
