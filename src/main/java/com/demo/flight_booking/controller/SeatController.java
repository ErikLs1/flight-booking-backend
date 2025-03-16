package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.dto.filter.SeatRecommendationDTO;
import com.demo.flight_booking.service.SeatService;
import com.demo.flight_booking.service.impl.SeatRecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Seat entities.
 *
 * <p>
 *     Provides endpoints for basic CRUD operations and additional endpoint for
 *     recommending seats based on user defined filters.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/seat")
public class SeatController implements BasicController<SeatDTO, Long> {
    private final SeatService seatService;
    private final SeatRecommendationService seatRecommendationService;

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

    /**
     * Recommends seats based on the provided filters.
     *
     * @param filter filters applied by the user.
     * @return a list of SeatDTO objects that match the user preferences.
     */
    @PostMapping("/recommend")
    public ResponseEntity<List<SeatDTO>> recommendSeats(@RequestBody SeatRecommendationDTO filter) {
        List<SeatDTO> recommended = seatRecommendationService.recommendSeats(filter);
        return ResponseEntity.ok(recommended);
    }
}
