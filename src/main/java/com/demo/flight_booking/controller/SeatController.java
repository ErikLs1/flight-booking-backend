package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/seat")
public class SeatController implements BasicController<SeatDTO, Long> {
    private final SeatService seatService;

    @Override
    public ResponseEntity<SeatDTO> create(SeatDTO dto) {
        SeatDTO saved = seatService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SeatDTO> update(Long id, SeatDTO dto) {
        SeatDTO updated = seatService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<SeatDTO> getById(Long id) {
        SeatDTO seatDTO = seatService.getById(id);
        return ResponseEntity.ok(seatDTO);
    }

    @Override
    public ResponseEntity<List<SeatDTO>> getAll() {
        List<SeatDTO> seatDTOS = seatService.getAll();
        return ResponseEntity.ok(seatDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        seatService.delete(id);
        return ResponseEntity.ok("Seat deleted!");
    }
}
