package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.AirlineDTO;
import com.demo.flight_booking.service.AirlineService;
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
@RequestMapping("/api/airline")
public class AirlineController implements BasicController<AirlineDTO, Long>{
    private final AirlineService airlineService;

    @Override
    public ResponseEntity<AirlineDTO> create(AirlineDTO dto) {
        AirlineDTO saved = airlineService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AirlineDTO> update(Long id, AirlineDTO dto) {
        AirlineDTO updated = airlineService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<AirlineDTO> getById(Long id) {
        AirlineDTO airlineDTO = airlineService.getById(id);
        return ResponseEntity.ok(airlineDTO);
    }

    @Override
    public ResponseEntity<List<AirlineDTO>> getAll() {
        List<AirlineDTO> airlineDTOS = airlineService.getAll();
        return ResponseEntity.ok(airlineDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        airlineService.delete(id);
        return ResponseEntity.ok("Airline deleted!");
    }
}
