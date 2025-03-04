package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.AirlineDTO;
import com.demo.flight_booking.service.AirlineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AirlineServiceImpl implements AirlineService {
    @Override
    public AirlineDTO create(AirlineDTO dto) {
        return null;
    }

    @Override
    public AirlineDTO update(Long id, AirlineDTO dto) {
        return null;
    }

    @Override
    public AirlineDTO getById(Long id) {
        return null;
    }

    @Override
    public List<AirlineDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
