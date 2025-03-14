package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.FlightInfoDTO;
import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.dto.TicketDTO;
import com.demo.flight_booking.dto.TicketInfoDTO;
import com.demo.flight_booking.mapper.TicketMapper;
import com.demo.flight_booking.model.Ticket;
import com.demo.flight_booking.repository.TicketRepository;
import com.demo.flight_booking.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketDTO create(TicketDTO dto) {
        Ticket ticket = ticketMapper.toEntity(dto);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toDTO(saved);
    }

    @Override
    public TicketDTO update(Long id, TicketDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        ticket.setTicketPrice(dto.getTicketPrice());
//        ticket.setBookingDate(dto.getBookingDate());

        Ticket updated = ticketRepository.save(ticket);
        return ticketMapper.toDTO(updated);
    }

    @Override
    public TicketDTO getById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return ticketMapper.toDTO(ticket);
    }

    @Override
    public List<TicketDTO> getAll() {
        return ticketRepository.findAll().stream()
                .map(ticketMapper:: toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw  new RuntimeException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketInfoDTO> getTicketsByEmail(String email) {
        List<Ticket> tickets = ticketRepository.findByPerson_Email(email);
        return tickets.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private TicketInfoDTO convertToDTO(Ticket ticket) {
        TicketInfoDTO dto = new TicketInfoDTO();
        dto.setTicketId(ticket.getTicketId());
        dto.setTicketPrice(ticket.getTicketPrice());
        dto.setSeatNumber(ticket.getFlightSeat().getSeat().getSeatNumber());
        dto.setSeatClass(ticket.getFlightSeat().getSeat().getSeatClass().getSeatClassName().name());

        // Map person
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonId(ticket.getPerson().getPersonId());
        personDTO.setFirstName(ticket.getPerson().getFirstName());
        personDTO.setLastName(ticket.getPerson().getLastName());
        personDTO.setEmail(ticket.getPerson().getEmail());
        personDTO.setPhone(ticket.getPerson().getPhone());
        dto.setPerson(personDTO);

        // Map flight
        FlightInfoDTO flightInfoDTO = new FlightInfoDTO();
        flightInfoDTO.setFlightId(ticket.getFlightSeat().getFlight().getFlightId());
        flightInfoDTO.setFlightNumber(ticket.getFlightSeat().getFlight().getFlightNumber());
        flightInfoDTO.setDepartureCity(ticket.getFlightSeat().getFlight().getDepartureAirport().getAirportCity());
        flightInfoDTO.setDepartureCountry(ticket.getFlightSeat().getFlight().getDepartureAirport().getAirportCountry());
        flightInfoDTO.setArrivalCity(ticket.getFlightSeat().getFlight().getArrivalAirport().getAirportCity());
        flightInfoDTO.setArrivalCountry(ticket.getFlightSeat().getFlight().getArrivalAirport().getAirportCountry());
        flightInfoDTO.setDepartureTime(ticket.getFlightSeat().getFlight().getDepartureTime());
        flightInfoDTO.setArrivalTime(ticket.getFlightSeat().getFlight().getArrivalTime());
        dto.setFlight(flightInfoDTO);

        return dto;
    }
}
