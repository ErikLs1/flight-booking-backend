package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.AirlineDTO;
import com.demo.flight_booking.mapper.AirlineMapper;
import com.demo.flight_booking.model.Airline;
import com.demo.flight_booking.repository.AirlineRepository;
import com.demo.flight_booking.service.AirlineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the Airline service interface.
 *
 * <p>
 *     Provides basic CRUD operations.
 * </p>
 */
@Service
@AllArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    @Override
    public AirlineDTO create(AirlineDTO dto) {
       Airline airline = airlineMapper.toEntity(dto);
       Airline saved = airlineRepository.save(airline);
       return airlineMapper.toDTO(saved);
    }

    @Override
    public AirlineDTO update(Long id, AirlineDTO dto) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with id: " + id));

        airline.setAirlineName(dto.getAirlineName());
        airline.setAirlineIATACode(dto.getAirlineIATACode());

        Airline updated = airlineRepository.save(airline);
        return airlineMapper.toDTO(updated);
    }

    @Override
    public AirlineDTO getById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with id: " + id));
        return airlineMapper.toDTO(airline);
    }

    @Override
    public List<AirlineDTO> getAll() {
        return airlineRepository.findAll().stream()
                .map(airlineMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new RuntimeException("Airline not found with id: " + id);
        }
        airlineRepository.deleteById(id);
    }
}
