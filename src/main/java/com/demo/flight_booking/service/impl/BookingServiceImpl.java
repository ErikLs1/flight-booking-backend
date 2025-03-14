package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.booking.BookingRequestDTO;
import com.demo.flight_booking.dto.booking.BookingResponseDTO;
import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.mapper.BookingMapper;
import com.demo.flight_booking.model.*;
import com.demo.flight_booking.model.enums.PaymentStatus;
import com.demo.flight_booking.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl {

    private final PersonRepository personRepository;
    private final FlightSeatRepository flightSeatRepository;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Transactional
    public BookingResponseDTO bookFlight(BookingRequestDTO bookingRequest) {
        Flight flight = flightRepository.findById(bookingRequest.getFlightId())
                .orElseThrow(() -> new RuntimeException("Could not find the flight with id: " + bookingRequest.getFlightId()));

        if (bookingRequest.getPassengers().size() != bookingRequest.getSeatIds().size()) {
            throw new RuntimeException("The number of passengers chosen seats do not match.");
        }

        // Create booking
        Booking booking = Booking.builder()
                .bookingDate(LocalDateTime.now())
                .build();
        booking = bookingRepository.save(booking);

        List<Ticket> tickets = new ArrayList<>();
        double totalAmount = 0.0;
        for (int i = 0; i < bookingRequest.getPassengers().size(); i++) {
            PersonDTO personDTO = bookingRequest.getPassengers().get(i);
            Long seatId = bookingRequest.getSeatIds().get(i);

            Person person = personRepository.findByEmail(personDTO.getEmail());
            if (person == null) {
                person = Person.builder()
                        .firstName(personDTO.getFirstName())
                        .lastName(personDTO.getLastName())
                        .email(personDTO.getEmail())
                        .phone(personDTO.getPhone())
                        .build();
            }

            person = personRepository.save(person);

            FlightSeat flightSeat = flightSeatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Could not find seat with id: " + seatId));

            if (flightSeat.getIsBooked()) {
                throw new RuntimeException("Seat with id " + seatId + " is already booked.");
            }

            flightSeat.setIsBooked(true);
            flightSeatRepository.save(flightSeat);

            // Calculate ticket price
            double additionalFee = flightSeat.getSeat().getSeatClass().getBasePrice();
            double ticketPrice = flight.getBasePrice() + additionalFee;

            totalAmount += ticketPrice;
            Ticket ticket = Ticket.builder()
                    .person(person)
                    .flightSeat(flightSeat)
                    .ticketPrice(ticketPrice)
                    .booking(booking)
                    .build();
            ticket = ticketRepository.save(ticket);
            tickets.add(ticket);
        }

        // Connect all tickets to one booking
        booking.setTickets(tickets);
        booking = bookingRepository.save(booking);

        Payment payment = Payment.builder()
                .booking(booking)
                .amount(totalAmount)
                .paymentStatus(PaymentStatus.COMPLETED)
                .paymentMethod(bookingRequest.getPaymentMethod())
                .build();

        payment = paymentRepository.save(payment);
        booking.setPayment(payment);
        booking = bookingRepository.save(booking);
        return bookingMapper.toDTO(booking);
    }
}
