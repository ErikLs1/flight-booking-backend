package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.AirportDTO;
import com.demo.flight_booking.mapper.AirportMapper;
import com.demo.flight_booking.model.Airport;
import com.demo.flight_booking.repository.AirportRepository;
import com.demo.flight_booking.service.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Override
    public AirportDTO create(AirportDTO dto) {
        Airport airport  = airportMapper.toEntity(dto);
        Airport saved  = airportRepository.save(airport);
        return airportMapper.toDTO(saved);
    }

    @Override
    public AirportDTO update(Long id, AirportDTO dto) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));

        airport.setAirportCode(dto.getAirportCode());
        airport.setAirportName(dto.getAirportName());
        airport.setAirportCity(dto.getAirportCity());
        airport.setAirportCountry(dto.getAirportCountry());

        Airport updated = airportRepository.save(airport);
        return airportMapper.toDTO(updated);
    }

    @Override
    public AirportDTO getById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));
        return airportMapper.toDTO(airport);
    }

    @Override
    public List<AirportDTO> getAll() {
        return airportRepository.findAll().stream()
                .map(airportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new RuntimeException("Airport not found with id: " + id);
        }
        airportRepository.deleteById(id);
    }
}
