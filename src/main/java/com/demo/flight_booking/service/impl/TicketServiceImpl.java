package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.service.TicketService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    @Override
    public TicketDTO create(TicketDTO dto) {
        return null;
    }

    @Override
    public TicketDTO update(Long id, TicketDTO dto) {
        return null;
    }

    @Override
    public TicketDTO getById(Long id) {
        return null;
    }

    @Override
    public List<TicketDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
