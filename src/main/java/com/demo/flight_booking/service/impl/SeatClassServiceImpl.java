package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.SeatClassDTO;
import com.demo.flight_booking.mapper.SeatClassMapper;
import com.demo.flight_booking.model.SeatClass;
import com.demo.flight_booking.repository.SeatClassRepository;
import com.demo.flight_booking.service.SeatClassService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SeatClassServiceImpl implements SeatClassService {

    private final SeatClassRepository seatClassRepository;
    private final SeatClassMapper seatClassMapper;

    @Override
    public SeatClassDTO create(SeatClassDTO dto) {
        SeatClass seatClass = seatClassMapper.toEntity(dto);
        SeatClass saved = seatClassRepository.save(seatClass);
        return seatClassMapper.toDTO(saved);
    }

    @Override
    public SeatClassDTO update(Long id, SeatClassDTO dto) {
        SeatClass seatClass = seatClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeatClass not found with id: " + id));

        seatClass.setExtraLegRoom(dto.getExtraLegRoom());
        seatClass.setNearExit(dto.getNearExit());
        seatClass.setWindow(dto.getWindow());
        seatClass.setAisle(dto.getAisle());
        seatClass.setBasePrice(dto.getBasePrice());

        SeatClass updated = seatClassRepository.save(seatClass);
        return seatClassMapper.toDTO(updated);
    }

    @Override
    public SeatClassDTO getById(Long id) {
        SeatClass seatClass = seatClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeatClass not found with id: " + id));
        return seatClassMapper.toDTO(seatClass);
    }

    @Override
    public List<SeatClassDTO> getAll() {
        return seatClassRepository.findAll().stream()
                .map(seatClassMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!seatClassRepository.existsById(id)) {
            throw new RuntimeException("SeatClass not found with id: " + id);
        }
        seatClassRepository.deleteById(id);
    }
}
