package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.SeatClassDTO;
import com.demo.flight_booking.service.SeatClassService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SeatClassServiceImpl implements SeatClassService {
    @Override
    public SeatClassDTO create(SeatClassDTO dto) {
        return null;
    }

    @Override
    public SeatClassDTO update(Long id, SeatClassDTO dto) {
        return null;
    }

    @Override
    public SeatClassDTO getById(Long id) {
        return null;
    }

    @Override
    public List<SeatClassDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
