package com.demo.flight_booking.unit_tests.service;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.SeatClassFeeDto;
import com.demo.flight_booking.dto.filter.FlightFilterDTO;
import com.demo.flight_booking.mapper.FlightMapper;
import com.demo.flight_booking.model.Aircraft;
import com.demo.flight_booking.model.Flight;
import com.demo.flight_booking.model.SeatClass;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.repository.FlightRepository;
import com.demo.flight_booking.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightServiceImpl flightService;

    private Flight flight;
    private Aircraft aircraft;
    private SeatClass seatClassEconomy;
    private SeatClass seatClassBusiness;

    @BeforeEach
    void setUp() {
        seatClassEconomy = new SeatClass();
        seatClassEconomy.setSeatClassName(SeatClassType.ECONOMY);
        seatClassEconomy.setBasePrice(20.0);

        seatClassBusiness = new SeatClass();
        seatClassBusiness.setSeatClassName(SeatClassType.BUSINESS);
        seatClassBusiness.setBasePrice(50.0);

        aircraft = new Aircraft();
        aircraft.setAircraftModel("Boeing 777");
        aircraft.setSeatClasses(Arrays.asList(seatClassEconomy, seatClassBusiness));

        flight = new Flight();
        flight.setFlightId(1L);
        flight.setFlightNumber("FL123");
        flight.setBasePrice(100.0);
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(3));
        flight.setAircraft(aircraft);
    }

    @Test
    void testGetAircraftModelByFlightId() {
        when(flightRepository.findAircraftModelByFlightId(1L)).thenReturn("Boeing 777");

        String model = flightService.getAircraftModelByFlightId(1L);
        assertNotNull(model);
        assertEquals("Boeing 777", model);
    }

    @Test
    void testGetSeatClassesByFlightId() {
        List<SeatClassType> seatClasses = Arrays.asList(SeatClassType.ECONOMY, SeatClassType.BUSINESS);
        when(flightRepository.findSeatClassesByFlightId(1L)).thenReturn(seatClasses);

        List<SeatClassType> result = flightService.getSeatClassesByFlightId(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(SeatClassType.ECONOMY));
        assertTrue(result.contains(SeatClassType.BUSINESS));
    }

    @Test
    void testGetFlightCitiesByFlightId() {
        when(flightRepository.findFlightCitiesByFlightId(1L)).thenReturn(Collections.singletonList("New York,London"));

        List<String> cities = flightService.getFlightCitiesByFlightId(1L);

        assertNotNull(cities);
        assertEquals(2, cities.size());
        assertEquals("New York", cities.get(0));
        assertEquals("London", cities.get(1));
    }

    @Test
    void testGetSeatClassesForFlight() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        List<SeatClassFeeDto> fees = flightService.getSeatClassesForFlight(1L);
        assertNotNull(fees);
        assertEquals(2, fees.size());
        fees.forEach(fee -> assertNotNull(fee.getSeatClassName()));
        fees.forEach(fee -> assertTrue(fee.getBaseFee() > 0));
    }

    @Test
    void testSearchFlights_NoPriceFilter() {
        FlightFilterDTO filter = new FlightFilterDTO();
        filter.setDepartureStartTime(LocalDateTime.now().plusDays(1));
        filter.setDepartureEndTime(LocalDateTime.now().plusDays(2));
        filter.setDepartureCity("New York");
        filter.setArrivalCity("London");
        filter.setAirlineName("American Airlines");
        filter.setMaxPrice(null);

        when(flightRepository.filterFlights(
                eq("New York"),
                eq("London"),
                eq("American Airlines"),
                isNull(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(Collections.singletonList(flight));

        FlightDTO dto = new FlightDTO();
        dto.setFlightId(flight.getFlightId());
        dto.setFlightNumber(flight.getFlightNumber());
        when(flightMapper.toDTO(flight)).thenReturn(dto);

        List<FlightDTO> result = flightService.searchFlights(filter);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("FL123", result.get(0).getFlightNumber());
    }

    @Test
    void testSearchFlights_WithPriceFilter() {
        FlightFilterDTO filter = new FlightFilterDTO();
        filter.setDepartureStartTime(LocalDateTime.now().plusDays(1));
        filter.setDepartureEndTime(LocalDateTime.now().plusDays(2));
        filter.setMaxPrice(150.0);

        when(flightRepository.filterFlights(
                any(),
                any(),
                any(),
                eq(150.0),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(Collections.singletonList(flight));

        FlightDTO dto = new FlightDTO();
        dto.setFlightId(flight.getFlightId());
        when(flightMapper.toDTO(flight)).thenReturn(dto);

        List<FlightDTO> result = flightService.searchFlights(filter);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testSearchFlights_MultipleFlights_FilterByPrice() {
        // 150.0
        Flight flight1 = flight;

        // final price = 120 + 50 = 170.0
        Flight flight2 = new Flight();
        flight2.setFlightId(2L);
        flight2.setFlightNumber("HS434");
        flight2.setBasePrice(120.0);
        flight2.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight2.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        flight2.setAircraft(aircraft);

        // final price = 80 + 50 = 130.0
        Flight flight3 = new Flight();
        flight3.setFlightId(3L);
        flight3.setFlightNumber("GF345");
        flight3.setBasePrice(80.0);
        flight3.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight3.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(3));
        flight3.setAircraft(aircraft);

        List<Flight> flights = Arrays.asList(flight1, flight2, flight3);

        FlightFilterDTO filter = new FlightFilterDTO();
        filter.setDepartureStartTime(LocalDateTime.now().plusDays(1));
        filter.setDepartureEndTime(LocalDateTime.now().plusDays(2));
        filter.setMaxPrice(160.0);

        when(flightRepository.filterFlights(
                any(),
                any(),
                any(),
                eq(160.0),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(flights);

        FlightDTO dto1 = new FlightDTO();
        dto1.setFlightId(flight1.getFlightId());
        dto1.setFlightNumber(flight1.getFlightNumber());

        FlightDTO dto3 = new FlightDTO();
        dto3.setFlightId(flight3.getFlightId());
        dto3.setFlightNumber(flight3.getFlightNumber());

        when(flightMapper.toDTO(flight1)).thenReturn(dto1);
        when(flightMapper.toDTO(flight3)).thenReturn(dto3);

        List<FlightDTO> result = flightService.searchFlights(filter);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> "FL123".equals(f.getFlightNumber())));
        assertTrue(result.stream().anyMatch(f -> "GF345".equals(f.getFlightNumber())));
    }

    @Test
    void testSearchFlights_MultipleFlights_NoMatch() {

        // final price 250
        Flight flight2 = new Flight();
        flight2.setFlightId(2L);
        flight2.setFlightNumber("FL124");
        flight2.setBasePrice(200.0);
        flight2.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight2.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        flight2.setAircraft(aircraft);

        // final price 230
        Flight flight3 = new Flight();
        flight3.setFlightId(3L);
        flight3.setFlightNumber("FL125");
        flight3.setBasePrice(180.0);
        flight3.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight3.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(3));
        flight3.setAircraft(aircraft);

        List<Flight> flights = Arrays.asList(flight2, flight3);

        FlightFilterDTO filter = new FlightFilterDTO();
        filter.setDepartureStartTime(LocalDateTime.now().plusDays(1));
        filter.setDepartureEndTime(LocalDateTime.now().plusDays(2));
        filter.setMaxPrice(100.0);

        when(flightRepository.filterFlights(
                any(),
                any(),
                any(),
                eq(100.0),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(flights);

        List<FlightDTO> result = flightService.searchFlights(filter);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testSearchFlights_FilterByCity() {
        FlightFilterDTO filter = new FlightFilterDTO();
        filter.setDepartureCity("New York");
        filter.setArrivalCity("London");
        filter.setDepartureStartTime(LocalDateTime.now().plusDays(1));
        filter.setDepartureEndTime(LocalDateTime.now().plusDays(2));
        filter.setAirlineName(null);
        filter.setMaxPrice(null);

        when(flightRepository.filterFlights(
                eq("New York"),
                eq("London"),
                isNull(),
                isNull(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(Collections.singletonList(flight));

        FlightDTO dto = new FlightDTO();
        dto.setFlightId(flight.getFlightId());
        dto.setFlightNumber(flight.getFlightNumber());
        when(flightMapper.toDTO(flight)).thenReturn(dto);

        List<FlightDTO> result = flightService.searchFlights(filter);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("FL123", result.get(0).getFlightNumber());
    }

    @Test
    void testSearchFlights_FilterByAirline() {
        FlightFilterDTO filter = new FlightFilterDTO();
        filter.setAirlineName("American Airlines");
        filter.setDepartureStartTime(LocalDateTime.now().plusDays(1));
        filter.setDepartureEndTime(LocalDateTime.now().plusDays(2));
        filter.setDepartureCity(null);
        filter.setArrivalCity(null);
        filter.setMaxPrice(null);

        when(flightRepository.filterFlights(
                isNull(),
                isNull(),
                eq("American Airlines"),
                isNull(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(Collections.singletonList(flight));

        FlightDTO dto = new FlightDTO();
        dto.setFlightId(flight.getFlightId());
        dto.setFlightNumber(flight.getFlightNumber());
        when(flightMapper.toDTO(flight)).thenReturn(dto);

        List<FlightDTO> result = flightService.searchFlights(filter);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("FL123", result.get(0).getFlightNumber());
    }
}
