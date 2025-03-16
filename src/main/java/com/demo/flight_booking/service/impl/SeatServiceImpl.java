package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.mapper.SeatMapper;
import com.demo.flight_booking.model.Seat;
import com.demo.flight_booking.repository.SeatRepository;
import com.demo.flight_booking.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  Implements the Seat service interface.
 *  <p>
 *      Provides basic CRUD operations.
 *  </p>
 */
@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    @Override
    public SeatDTO create(SeatDTO dto) {
        Seat seat = seatMapper.toEntity(dto);
        Seat saved = seatRepository.save(seat);
        return seatMapper.toDTO(saved);
    }

    @Override
    public SeatDTO update(Long id, SeatDTO dto) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));

        seat.setSeatNumber(dto.getSeatNumber());
        seat.setRowNumber(dto.getRowNumber());
        seat.setSeatLetter(dto.getSeatLetter());
        seat.setExtraLegRoom(dto.getExtraLegRoom());
        seat.setNearExit(dto.getNearExit());
        seat.setWindow(dto.getWindow());
        seat.setAisle(dto.getAisle());

        Seat updated = seatRepository.save(seat);
        return seatMapper.toDTO(updated);
    }

    @Override
    public SeatDTO getById(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));
        return seatMapper.toDTO(seat);
    }

    @Override
    public List<SeatDTO> getAll() {
        return seatRepository.findAll().stream()
                .map(seatMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new RuntimeException("Seat not found with id: " + id);
        }
        seatRepository.deleteById(id);;
    }
}
