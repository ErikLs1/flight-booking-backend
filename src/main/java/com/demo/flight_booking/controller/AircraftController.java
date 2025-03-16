package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.AircraftDTO;
import com.demo.flight_booking.service.AircraftService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest controller for managing Aircraft entities.
 *
 * <p>
 *     Provides endpoints to create, update, retrieve and delete aircraft.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/aircraft")
public class AircraftController implements BasicController<AircraftDTO, Long> {

    private final AircraftService aircraftService;

    @Override
    public ResponseEntity<AircraftDTO> create(AircraftDTO dto) {
        AircraftDTO saved = aircraftService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AircraftDTO> update(Long id, AircraftDTO dto) {
        AircraftDTO updated = aircraftService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<AircraftDTO> getById(Long id) {
        AircraftDTO aircraftDTO = aircraftService.getById(id);
        return ResponseEntity.ok(aircraftDTO);
    }

    @Override
    public ResponseEntity<List<AircraftDTO>> getAll() {
        List<AircraftDTO> aircraftDTOS = aircraftService.getAll();
        return ResponseEntity.ok(aircraftDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        aircraftService.delete(id);
        return ResponseEntity.ok("Aircraft deleted!");
    }
}
