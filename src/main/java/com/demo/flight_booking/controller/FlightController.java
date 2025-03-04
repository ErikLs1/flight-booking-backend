package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.service.FlightService;
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
@RequestMapping("/api/flight")
public class FlightController implements BasicController<FlightDTO, Long> {
    private final FlightService flightService;

    @Override
    public ResponseEntity<FlightDTO> create(FlightDTO dto) {
        FlightDTO saved = flightService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FlightDTO> update(Long id, FlightDTO dto) {
        FlightDTO updated = flightService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<FlightDTO> getById(Long id) {
        FlightDTO flightDTO = flightService.getById(id);
        return ResponseEntity.ok(flightDTO);
    }

    @Override
    public ResponseEntity<List<FlightDTO>> getAll() {
        List<FlightDTO> flightDTOS = flightService.getAll();
        return ResponseEntity.ok(flightDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        flightService.delete(id);
        return ResponseEntity.ok("Flight deleted!");
    }
}
