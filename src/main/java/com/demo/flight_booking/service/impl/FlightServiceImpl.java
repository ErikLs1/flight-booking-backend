package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.service.FlightService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {
    @Override
    public FlightDTO create(FlightDTO dto) {
        return null;
    }

    @Override
    public FlightDTO update(Long id, FlightDTO dto) {
        return null;
    }

    @Override
    public FlightDTO getById(Long id) {
        return null;
    }

    @Override
    public List<FlightDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
