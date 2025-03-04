package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {
    @Override
    public SeatDTO create(SeatDTO dto) {
        return null;
    }

    @Override
    public SeatDTO update(Long id, SeatDTO dto) {
        return null;
    }

    @Override
    public SeatDTO getById(Long id) {
        return null;
    }

    @Override
    public List<SeatDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
