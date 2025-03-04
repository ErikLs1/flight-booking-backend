package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.FlightSeatDTO;
import com.demo.flight_booking.service.FlightSeatService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlightSeatServiceImpl implements FlightSeatService {
    @Override
    public FlightSeatDTO create(FlightSeatDTO dto) {
        return null;
    }

    @Override
    public FlightSeatDTO update(Long id, FlightSeatDTO dto) {
        return null;
    }

    @Override
    public FlightSeatDTO getById(Long id) {
        return null;
    }

    @Override
    public List<FlightSeatDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
