package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.AirportDTO;
import com.demo.flight_booking.service.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for managing Airport entities.
 *
 * <p>
 *     Provides endpoints ro create, update, retrieve and delete airports.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/airport")
public class AirportController implements BasicController<AirportDTO, Long> {
    private final AirportService airportService;

    @Override
    public ResponseEntity<AirportDTO> create(AirportDTO dto) {
        AirportDTO saved = airportService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AirportDTO> update(Long id, AirportDTO dto) {
        AirportDTO updated = airportService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<AirportDTO> getById(Long id) {
        AirportDTO airportDTO = airportService.getById(id);
        return ResponseEntity.ok(airportDTO);
    }

    @Override
    public ResponseEntity<List<AirportDTO>> getAll() {
        List<AirportDTO> airportDTOS = airportService.getAll();
        return ResponseEntity.ok(airportDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        airportService.delete(id);
        return ResponseEntity.ok("Airport deleted!");
    }
}
