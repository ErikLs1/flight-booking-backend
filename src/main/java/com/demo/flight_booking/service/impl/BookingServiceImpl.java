package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.booking.BookingDTO;
import com.demo.flight_booking.dto.booking.BookingRequestDTO;
import com.demo.flight_booking.dto.booking.BookingResponseDTO;
import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.excpetion.booking.PassengerCountMismatchException;
import com.demo.flight_booking.excpetion.booking.SeatNotFoundException;
import com.demo.flight_booking.excpetion.booking.FlightNoFoundException;
import com.demo.flight_booking.excpetion.booking.SeatAlreadyBookedException;
import com.demo.flight_booking.mapper.BookingMapper;
import com.demo.flight_booking.model.*;
import com.demo.flight_booking.model.enums.PaymentStatus;
import com.demo.flight_booking.repository.*;
import com.demo.flight_booking.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final PersonRepository personRepository;
    private final FlightSeatRepository flightSeatRepository;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Transactional
    @Override
    public BookingResponseDTO bookFlight(BookingRequestDTO bookingRequest) {
        Flight flight = flightRepository.findById(bookingRequest.getFlightId())
                .orElseThrow(() -> new FlightNoFoundException("Could not find the flight with id: " + bookingRequest.getFlightId()));

        if (bookingRequest.getPassengers().size() != bookingRequest.getSeatIds().size()) {
            throw new PassengerCountMismatchException(
                    "The number of passengers chosen seats do not match. Passenger received: " +
                    bookingRequest.getPassengers().size() +
                    " .Seats received: " + bookingRequest.getSeatIds().size());
        }

        for (int i = 0; i < bookingRequest.getPassengers().size(); i++) {
            PersonDTO personDTO = bookingRequest.getPassengers().get(i);
            Long seatId = bookingRequest.getSeatIds().get(i);

            FlightSeat flightSeat = flightSeatRepository.findById(seatId)
                    .orElseThrow(() -> new SeatNotFoundException("Could not find seat with id: " + seatId));

            if (flightSeat.getIsBooked()) {
                throw new SeatAlreadyBookedException("Seat with id " + seatId + " is already booked.");
            }
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
            FlightSeat flightSeat = flightSeatRepository.findById(seatId).get();

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

    // Not implemented
    @Override
    public BookingDTO create(BookingDTO dto) {
        return null;
    }

    // Not implemented
    @Override
    public BookingDTO update(Long aLong, BookingDTO dto) {
        return null;
    }

    // Not implemented
    @Override
    public BookingDTO getById(Long aLong) {
        return null;
    }

    // Not implemented
    @Override
    public List<BookingDTO> getAll() {
        return List.of();
    }

    // Not implemented
    @Override
    public void delete(Long aLong) {

    }
}
