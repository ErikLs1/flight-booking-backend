package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.SeatClassDTO;
import com.demo.flight_booking.service.SeatClassService;
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
@RequestMapping("/api/seatClass")
public class SeatClassController implements BasicController<SeatClassDTO, Long> {
    private final SeatClassService seatClassService;

    @Override
    public ResponseEntity<SeatClassDTO> create(SeatClassDTO dto) {
        SeatClassDTO saved = seatClassService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SeatClassDTO> update(Long id, SeatClassDTO dto) {
        SeatClassDTO updated = seatClassService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<SeatClassDTO> getById(Long id) {
        SeatClassDTO seatClassDTO = seatClassService.getById(id);
        return ResponseEntity.ok(seatClassDTO);
    }

    @Override
    public ResponseEntity<List<SeatClassDTO>> getAll() {
        List<SeatClassDTO> seatClassDTOS = seatClassService.getAll();
        return ResponseEntity.ok(seatClassDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        seatClassService.delete(id);
        return ResponseEntity.ok("SeatClass deleted!");
    }
}
