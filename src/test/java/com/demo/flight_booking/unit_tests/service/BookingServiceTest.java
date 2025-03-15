package com.demo.flight_booking.unit_tests.service;

import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.dto.booking.BookingRequestDTO;
import com.demo.flight_booking.dto.booking.BookingResponseDTO;
import com.demo.flight_booking.excpetion.booking.PassengerCountMismatchException;
import com.demo.flight_booking.excpetion.booking.SeatNotFoundException;
import com.demo.flight_booking.excpetion.booking.FlightNoFoundException;
import com.demo.flight_booking.excpetion.booking.SeatAlreadyBookedException;
import com.demo.flight_booking.mapper.BookingMapper;
import com.demo.flight_booking.model.*;
import com.demo.flight_booking.model.enums.PaymentMethod;
import com.demo.flight_booking.model.enums.PaymentStatus;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.repository.*;
import com.demo.flight_booking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private FlightSeatRepository flightSeatRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Flight flight;
    private FlightSeat flightSeat;
    private Seat seat;
    private Airline airline;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Aircraft aircraft;
    private SeatClass seatClass;

    @BeforeEach
    void setUp() {
        airline = new Airline();
        airline.setAirlineName("American Airlines");
        airline.setAirlineIATACode("AA");

        departureAirport = new Airport();
        departureAirport.setAirportCode("JFK");
        departureAirport.setAirportName("John F. Kennedy International Airport");
        departureAirport.setAirportCity("New York");
        departureAirport.setAirportCountry("USA");

        arrivalAirport = new Airport();
        arrivalAirport.setAirportCode("LHR");
        arrivalAirport.setAirportName("London Heathrow Airport");
        arrivalAirport.setAirportCity("London");
        arrivalAirport.setAirportCountry("United Kingdom");

        aircraft = new Aircraft();
        aircraft.setAircraftModel("Boeing 737");
        aircraft.setAircraftTotalCapacity(180);
        aircraft.setAircraftEconomySeats(90);
        aircraft.setAircraftBusinessSeats(60);
        aircraft.setAircraftFirstClassSeats(30);

        // Dummy data
        flight = new Flight();
        flight.setFlightNumber("FL123");
        flight.setBasePrice(100.0);
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(3));
        flight.setAirline(airline);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setAircraft(aircraft);

        seatClass = new SeatClass();
        seatClass.setSeatClassName(SeatClassType.BUSINESS);
        seatClass.setBasePrice(50.0);
        seatClass.setAircraft(aircraft);

        seat = new Seat();
        seat.setSeatNumber("1A");
        seat.setRowNumber(1);
        seat.setSeatLetter("1");
        seat.setAircraft(aircraft);
        seat.setSeatClass(seatClass);
        seat.setAisle(false);
        seat.setWindow(true);
        seat.setNearExit(false);
        seat.setExtraLegRoom(true);

        flightSeat = new FlightSeat();
        flightSeat.setFlight(flight);
        flightSeat.setSeat(seat);
        flightSeat.setIsBooked(false);
    }

    @Test
    void testFlightNotFoundException() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("John");
        personDTO.setLastName("Devol");
        personDTO.setEmail("john.devol@gmail.com");
        personDTO.setPhone("+1234567");

        BookingRequestDTO request = new BookingRequestDTO();
        request.setFlightId(7777L);
        request.setPassengers(Collections.singletonList(personDTO));
        request.setSeatIds(Collections.singletonList(1L));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        when(flightRepository.findById(7777L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(FlightNoFoundException.class, () -> bookingService.bookFlight(request));
        assertTrue(ex.getMessage().contains("Could not find the flight"));
        assertEquals(0, bookingRepository.count(),
                "Expected 0 bookings, but found " + bookingRepository.count());
    }

    @Test
    void testPassengerCountMismatchException() {
        PersonDTO person1 = new PersonDTO();
        person1.setFirstName("John");
        person1.setLastName("Devol");
        person1.setEmail("john.devol@gmail.com");
        person1.setPhone("+1234567");

        PersonDTO person2 = new PersonDTO();
        person2.setFirstName("Jane");
        person2.setLastName("Devol");
        person2.setEmail("jane.devol@gmail.com");
        person2.setPhone("+2345678");

        BookingRequestDTO request = new BookingRequestDTO();
        request.setFlightId(1L);
        request.setPassengers(Arrays.asList(person1, person2));
        request.setSeatIds(Collections.singletonList(1L));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Exception ex = assertThrows(PassengerCountMismatchException.class, () -> bookingService.bookFlight(request));
        assertTrue(ex.getMessage().contains("do not match"));
        assertEquals(0, bookingRepository.count(),
                "Expected 0 bookings, but found " + bookingRepository.count());
    }

    @Test
    void testSeatNotFoundException() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("John");
        personDTO.setLastName("Devol");
        personDTO.setEmail("john.devol@gmail.com");
        personDTO.setPhone("+1234567");

        BookingRequestDTO request = new BookingRequestDTO();
        request.setFlightId(1L);
        request.setPassengers(Collections.singletonList(personDTO));
        request.setSeatIds(Collections.singletonList(7777L));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightSeatRepository.findById(7777L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(SeatNotFoundException.class, () -> bookingService.bookFlight(request));
        assertTrue(ex.getMessage().contains("Could not find seat with id"));
        assertEquals(0, bookingRepository.count(),
                "Expected 0 bookings, but found " + bookingRepository.count());
    }

    @Test
    void testSeatAlreadyBookedException() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("John");
        personDTO.setLastName("Devol");
        personDTO.setEmail("john.devol@gmail.com");
        personDTO.setPhone("+1234567");

        flightSeat.setIsBooked(true);

        BookingRequestDTO request = new BookingRequestDTO();
        request.setFlightId(1L);
        request.setPassengers(Collections.singletonList(personDTO));
        request.setSeatIds(Collections.singletonList(1L));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightSeatRepository.findById(1L)).thenReturn(Optional.of(flightSeat));

        Exception ex = assertThrows(SeatAlreadyBookedException.class, () -> bookingService.bookFlight(request));
        assertTrue(ex.getMessage().contains("already booked"));
        assertEquals(0, bookingRepository.count(),
                "Expected 0 bookings, but found " + bookingRepository.count());
    }

    @Test
    void testBookFlight_Successful() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightSeatRepository.findById(1L)).thenReturn(Optional.of(flightSeat));
        when(personRepository.findByEmail("john.devol@gmail.com")).thenReturn(null);
        when(personRepository.save(any(Person.class))).thenAnswer(i -> i.getArguments()[0]);

        Booking savedBooking = Booking.builder().bookingDate(LocalDateTime.now()).build();
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment payment = Payment.builder()
                .amount(150.0)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        BookingResponseDTO responseDTO = new BookingResponseDTO();
        responseDTO.setBookingId(1L);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(responseDTO);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("John");
        personDTO.setLastName("Devol");
        personDTO.setEmail("john.devol@gmail.com");
        personDTO.setPhone("+1234567");

        BookingRequestDTO request = new BookingRequestDTO();
        request.setFlightId(1L);
        request.setPassengers(Collections.singletonList(personDTO));
        request.setSeatIds(Collections.singletonList(1L));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        BookingResponseDTO response = bookingService.bookFlight(request);

        assertNotNull(response);
        assertNotNull(response.getBookingId());
        assertEquals(PaymentMethod.CREDIT_CARD, payment.getPaymentMethod(),
                "Expected payment method is CREDIT_CARD, but received " + payment.getPaymentMethod());
        assertEquals(PaymentStatus.COMPLETED, payment.getPaymentStatus(),
                "Expected payment status is COMPLETED, but received " + payment.getPaymentStatus());
        assertEquals(150.0, payment.getAmount(),
                "Expected total amount is 150.0, but received " + payment.getAmount());
        verify(flightSeatRepository).save(any(FlightSeat.class));
    }

    @Test
    void testMultiplePassengerBooking_PriceCalculation() {
        Seat seat2 = new Seat();
        seat2.setSeatNumber("1B");
        seat2.setRowNumber(1);
        seat2.setSeatLetter("B");
        seat2.setAircraft(aircraft);
        seat2.setSeatClass(seatClass);
        seat2.setAisle(true);
        seat2.setWindow(false);
        seat2.setNearExit(false);
        seat2.setExtraLegRoom(true);

        FlightSeat flightSeat2 = new FlightSeat();
        flightSeat2.setFlight(flight);
        flightSeat2.setSeat(seat2);
        flightSeat2.setIsBooked(false);

        double expectedTotalAmount = 150.0 * 2;

        PersonDTO person1 = new PersonDTO();
        person1.setFirstName("John");
        person1.setLastName("Devol");
        person1.setEmail("john.devol@gmail.com");
        person1.setPhone("+1234567");

        PersonDTO person2 = new PersonDTO();
        person2.setFirstName("Bob");
        person2.setLastName("Devol");
        person2.setEmail("bob.devol@gmail.com");
        person2.setPhone("+2345679");

        BookingRequestDTO request = new BookingRequestDTO();
        request.setFlightId(1L);
        request.setPassengers(Arrays.asList(person1, person2));
        request.setSeatIds(Arrays.asList(1L, 2L));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightSeatRepository.findById(1L)).thenReturn(Optional.of(flightSeat));
        when(flightSeatRepository.findById(2L)).thenReturn(Optional.of(flightSeat2));

        when(personRepository.findByEmail("john.devol@gmail.com")).thenReturn(null);
        when(personRepository.findByEmail("bob.devol@gmail.com")).thenReturn(null);
        when(personRepository.save(any(Person.class))).thenAnswer(i -> i.getArguments()[0]);

        Booking savedBooking = Booking.builder().bookingDate(LocalDateTime.now()).build();
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment payment = Payment.builder()
                .amount(expectedTotalAmount)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        BookingResponseDTO responseDTO = new BookingResponseDTO();
        responseDTO.setBookingId(1L);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(responseDTO);

        BookingResponseDTO response = bookingService.bookFlight(request);
        assertNotNull(response);
        assertNotNull(response.getBookingId());
        assertEquals(expectedTotalAmount, payment.getAmount(),
                "Expected total amount is " + expectedTotalAmount + "but received " + payment.getAmount());
        verify(ticketRepository, times(2)).save(any(Ticket.class));
    }

    @Test
    void testDuplicatePersonNotCreated() {
        Person person1 = new Person();
        person1.setLastName("Jane");
        person1.setLastName("Smith");
        person1.setEmail("jane.smith@gmail.com");
        person1.setPhone("+123456");

        when(personRepository.findByEmail("jane.smith@gmail.com")).thenReturn(person1);

        PersonDTO person2 = new PersonDTO();
        person2.setFirstName("Jane");
        person2.setLastName("Smith");
        person2.setEmail("jane.smith@gmail.com");
        person2.setPhone("+123456");

        BookingRequestDTO request  =new BookingRequestDTO();
        request.setFlightId(1L);
        request.setPassengers(Collections.singletonList(person2));
        request.setSeatIds(Collections.singletonList(1L));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightSeatRepository.findById(1L)).thenReturn(Optional.of(flightSeat));
        when(personRepository.save(any(Person.class))).thenAnswer(i -> i.getArguments()[0]);

        Booking savedBooking = Booking.builder().bookingDate(LocalDateTime.now()).build();
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment payment = Payment.builder()
                .amount(150.0)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        BookingResponseDTO responseDTO = new BookingResponseDTO();
        responseDTO.setBookingId(1L);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(responseDTO);

        BookingResponseDTO response = bookingService.bookFlight(request);
        assertNotNull(response);
        assertEquals("Jane", person2.getFirstName(),
                "Expected firs name 'Jane', but found " + person2.getFirstName());
        assertEquals("Smith", person2.getLastName(),
                "Expected last name 'Smith', but found " + person2.getLastName());
        assertEquals("+123456", person2.getPhone(),
                "Expected phone '+123456', but found " + person2.getPhone());

        verify(personRepository).findByEmail("jane.smith@gmail.com");
    }

}

