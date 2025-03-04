package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.AircraftDTO;
import com.demo.flight_booking.service.AircraftService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AircraftServiceImpl implements AircraftService {
    @Override
    public AircraftDTO create(AircraftDTO dto) {
        return null;
    }

    @Override
    public AircraftDTO update(Long id, AircraftDTO dto) {
        return null;
    }

    @Override
    public AircraftDTO getById(Long id) {
        return null;
    }

    @Override
    public List<AircraftDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
