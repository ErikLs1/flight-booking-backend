package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.AirportDTO;
import com.demo.flight_booking.service.AirportService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AirportServiceImpl implements AirportService {
    @Override
    public AirportDTO create(AirportDTO dto) {
        return null;
    }

    @Override
    public AirportDTO update(Long id, AirportDTO dto) {
        return null;
    }

    @Override
    public AirportDTO getById(Long id) {
        return null;
    }

    @Override
    public List<AirportDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
