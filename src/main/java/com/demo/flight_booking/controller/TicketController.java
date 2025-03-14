package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.dto.TicketInfoDTO;
import com.demo.flight_booking.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/ticket")
public class TicketController implements BasicController<TicketDTO, Long> {
    private final TicketService ticketService;

    @Override
    public ResponseEntity<TicketDTO> create(TicketDTO dto) {
        TicketDTO saved = ticketService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TicketDTO> update(Long id, TicketDTO dto) {
        TicketDTO updated = ticketService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<TicketDTO> getById(Long id) {
        TicketDTO ticketDTO = ticketService.getById(id);
        return ResponseEntity.ok(ticketDTO);
    }

    @Override
    public ResponseEntity<List<TicketDTO>> getAll() {
        List<TicketDTO> ticketDTOS = ticketService.getAll();
        return ResponseEntity.ok(ticketDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        ticketService.delete(id);
        return ResponseEntity.ok("Ticket deleted!");
    }

    @GetMapping("/info")
    public ResponseEntity<List<TicketInfoDTO>> getTicketInfoByEmail(@RequestParam String email) {
        List<TicketInfoDTO> ticketInfoDTOS = ticketService.getTicketsByEmail(email);
        return ResponseEntity.ok(ticketInfoDTOS);
    }
}
