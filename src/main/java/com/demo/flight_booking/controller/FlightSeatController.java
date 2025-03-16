package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.FlightSeatDTO;
import com.demo.flight_booking.service.FlightSeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing flight seats.
 * <p>
 *     Provides basic CRUD endpoints and additional
 *     endpoint to retrieve seats for a specific flight.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/flightSeat")
public class FlightSeatController implements BasicController<FlightSeatDTO, Long> {
    private final FlightSeatService flightSeatService;

    @Override
    public ResponseEntity<FlightSeatDTO> create(FlightSeatDTO dto) {
        FlightSeatDTO saved = flightSeatService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FlightSeatDTO> update(Long id, FlightSeatDTO dto) {
        FlightSeatDTO updated = flightSeatService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<FlightSeatDTO> getById(Long id) {
        FlightSeatDTO flightSeatDTO = flightSeatService.getById(id);
        return ResponseEntity.ok(flightSeatDTO);
    }

    @Override
    public ResponseEntity<List<FlightSeatDTO>> getAll() {
        List<FlightSeatDTO> flightSeatDTOS = flightSeatService.getAll();
        return ResponseEntity.ok(flightSeatDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        flightSeatService.delete(id);
        return ResponseEntity.ok("FlightSeat deleted!");
    }

    /**
     * Retrieve all flight seats associated with a specific flight,
     * sorted by row and seat letter.
     *
     * @param flightId the id of the flight.
     * @return a list of FlightSeatDTO objects.
     */
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FlightSeatDTO>> getSeatsByFlightId(@PathVariable Long flightId) {
        List<FlightSeatDTO> seats = flightSeatService.getSeatByFlightId(flightId);
        return ResponseEntity.ok(seats);
    }
}
