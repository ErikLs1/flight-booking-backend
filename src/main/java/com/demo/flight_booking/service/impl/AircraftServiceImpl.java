package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.AircraftDTO;
import com.demo.flight_booking.mapper.AircraftMapper;
import com.demo.flight_booking.model.Aircraft;
import com.demo.flight_booking.repository.AircraftRepository;
import com.demo.flight_booking.service.AircraftService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    @Override
    public AircraftDTO create(AircraftDTO dto) {
        Aircraft aircraft = aircraftMapper.toEntity(dto);
        Aircraft saved = aircraftRepository.save(aircraft);
        return aircraftMapper.toDTO(saved);
    }

    @Override
    public AircraftDTO update(Long id, AircraftDTO dto) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found with id: " + id));

        aircraft.setAircraftModel(dto.getAircraftModel());
        aircraft.setAircraftTotalCapacity(dto.getAircraftTotalCapacity());
        aircraft.setAircraftEconomySeats(dto.getAircraftEconomySeats());
        aircraft.setAircraftPremiumSeats(dto.getAircraftPremiumSeats());
        aircraft.setAircraftBusinessSeats(dto.getAircraftBusinessSeats());
        aircraft.setAircraftFirstClassSeats(dto.getAircraftFirstClassSeats());

        Aircraft updated = aircraftRepository.save(aircraft);
        return aircraftMapper.toDTO(updated);
    }

    @Override
    public AircraftDTO getById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found with id: " + id));
        return aircraftMapper.toDTO(aircraft);
    }

    @Override
    public List<AircraftDTO> getAll() {
        return aircraftRepository.findAll().stream()
                .map(aircraftMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!aircraftRepository.existsById(id)) {
            throw new RuntimeException("Aircraft not found with id: " + id);
        }
        aircraftRepository.deleteById(id);
    }
}
