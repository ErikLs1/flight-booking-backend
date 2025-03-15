package com.demo.flight_booking.unit_tests.service;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.dto.filter.SeatRecommendationDTO;
import com.demo.flight_booking.mapper.SeatMapper;
import com.demo.flight_booking.model.FlightSeat;
import com.demo.flight_booking.model.Seat;
import com.demo.flight_booking.model.SeatClass;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.repository.FlightSeatRepository;
import com.demo.flight_booking.service.impl.SeatRecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeatRecommendationServiceTest {
    @Mock
    private FlightSeatRepository flightSeatRepository;

    @Mock
    private SeatMapper seatMapper;

    @InjectMocks
    private SeatRecommendationService seatRecommendationService;

    private Seat seatA1;
    private Seat seatA2;
    private Seat seatA3;
    private Seat seatB1;
    private Seat seatB2;

    private FlightSeat flightSeatA1;
    private FlightSeat flightSeatA2;
    private FlightSeat flightSeatA3;
    private FlightSeat flightSeatB1;
    private FlightSeat flightSeatB2;

    @BeforeEach
    void setUp() {
        SeatClass economyClass = new SeatClass();
        economyClass.setSeatClassName(SeatClassType.ECONOMY);

        seatA1 = new Seat();
        seatA1.setRowNumber(1);
        seatA1.setSeatLetter("A");
        seatA1.setAisle(false);
        seatA1.setWindow(true);
        seatA1.setNearExit(false);
        seatA1.setExtraLegRoom(true);
        seatA1.setSeatClass(economyClass);

        seatA2 = new Seat();
        seatA2.setRowNumber(1);
        seatA2.setSeatLetter("B");
        seatA2.setAisle(true);
        seatA2.setWindow(false);
        seatA2.setNearExit(false);
        seatA2.setExtraLegRoom(true);
        seatA2.setSeatClass(economyClass);

        seatA3 = new Seat();
        seatA3.setRowNumber(1);
        seatA3.setSeatLetter("C");
        seatA3.setAisle(false);
        seatA3.setWindow(true);
        seatA3.setNearExit(false);
        seatA3.setExtraLegRoom(true);
        seatA3.setSeatClass(economyClass);

        seatB1 = new Seat();
        seatB1.setRowNumber(2);
        seatB1.setSeatLetter("A");
        seatB1.setAisle(false);
        seatB1.setWindow(true);
        seatB1.setNearExit(false);
        seatB1.setExtraLegRoom(false);
        seatB1.setSeatClass(economyClass);

        seatB2 = new Seat();
        seatB2.setRowNumber(2);
        seatB2.setSeatLetter("B");
        seatB2.setAisle(true);
        seatB2.setWindow(false);
        seatB2.setNearExit(false);
        seatB2.setExtraLegRoom(false);
        seatB2.setSeatClass(economyClass);

        flightSeatA1 = new FlightSeat();
        flightSeatA1.setSeat(seatA1);
        flightSeatA1.setIsBooked(false);

        flightSeatA2 = new FlightSeat();
        flightSeatA2.setSeat(seatA2);
        flightSeatA2.setIsBooked(false);

        flightSeatA3 = new FlightSeat();
        flightSeatA3.setSeat(seatA3);
        flightSeatA3.setIsBooked(false);

        flightSeatB1 = new FlightSeat();
        flightSeatB1.setSeat(seatB1);
        flightSeatB1.setIsBooked(false);

        flightSeatB2 = new FlightSeat();
        flightSeatB2.setSeat(seatB2);
        flightSeatB2.setIsBooked(false);
    }

    @Test
    void testRecommendSeats_NonAdjacent_NoPreferences() {
        SeatRecommendationDTO filter = new SeatRecommendationDTO();
        filter.setFlightId(1L);
        filter.setAdjacentPreferred(false);
        filter.setSeatClassType(SeatClassType.ECONOMY);
        filter.setAislePreferred(null);
        filter.setWindowPreferred(null);
        filter.setNearExitPreferred(null);
        filter.setExtraLegRoomPreferred(null);

        List<FlightSeat> freeSeats = Arrays.asList(flightSeatA1, flightSeatA2, flightSeatB1);
        when(flightSeatRepository.findByFlight_FlightIdAndIsBookedFalse(1L)).thenReturn(freeSeats);

        SeatDTO seat1 = new SeatDTO();
        seat1.setSeatNumber("1A");
        SeatDTO seat2 = new SeatDTO();
        seat2.setSeatNumber("1B");
        SeatDTO seat3 = new SeatDTO();
        seat3.setSeatNumber("2A");

        when(seatMapper.toDTO(seatA1)).thenReturn(seat1);
        when(seatMapper.toDTO(seatA2)).thenReturn(seat2);
        when(seatMapper.toDTO(seatB1)).thenReturn(seat3);

        List<SeatDTO> recommended = seatRecommendationService.recommendSeats(filter);
        assertNotNull(recommended);
        assertEquals(3, recommended.size());
        assertTrue(recommended.stream().anyMatch(dto -> "1A".equals(dto.getSeatNumber())));
        assertTrue(recommended.stream().anyMatch(dto -> "1B".equals(dto.getSeatNumber())));
        assertTrue(recommended.stream().anyMatch(dto -> "2A".equals(dto.getSeatNumber())));
    }

    @Test
    void testRecommendSeats_NonAdjacent_WithPreferences() {
        SeatRecommendationDTO filter = new SeatRecommendationDTO();
        filter.setFlightId(1L);
        filter.setAdjacentPreferred(false);
        filter.setSeatClassType(SeatClassType.ECONOMY);
        filter.setExtraLegRoomPreferred(true);
        filter.setAislePreferred(null);
        filter.setWindowPreferred(null);
        filter.setNearExitPreferred(null);

        List<FlightSeat> freeSeats = Arrays.asList(flightSeatA1, flightSeatA2, flightSeatA3, flightSeatB1, flightSeatB2);
        when(flightSeatRepository.findByFlight_FlightIdAndIsBookedFalse(1L)).thenReturn(freeSeats);

        SeatDTO seat1 = new SeatDTO();
        seat1.setSeatNumber("1A");
        SeatDTO seat2 = new SeatDTO();
        seat2.setSeatNumber("1B");
        SeatDTO seat3 = new SeatDTO();
        seat3.setSeatNumber("1C");

        when(seatMapper.toDTO(seatA1)).thenReturn(seat1);
        when(seatMapper.toDTO(seatA2)).thenReturn(seat2);
        when(seatMapper.toDTO(seatA3)).thenReturn(seat3);

        List<SeatDTO> recommended = seatRecommendationService.recommendSeats(filter);
        assertNotNull(recommended);
        assertEquals(3, recommended.size());
        assertTrue(recommended.stream().allMatch(dto -> dto.getSeatNumber().matches("1[A-C]")));
    }

    @Test
    void testRecommendSeats_Adjacent() {
        SeatRecommendationDTO filter = new SeatRecommendationDTO();
        filter.setFlightId(1L);
        filter.setAdjacentPreferred(true);
        filter.setPassengerCount(2);
        filter.setSeatClassType(SeatClassType.ECONOMY);
        filter.setAislePreferred(null);
        filter.setWindowPreferred(null);
        filter.setNearExitPreferred(null);
        filter.setExtraLegRoomPreferred(null);

        when(flightSeatRepository.findFlightSeatsAndSortedByRowLetter(1L))
                .thenReturn(Arrays.asList(flightSeatA1, flightSeatA2, flightSeatA3));

        SeatDTO seat1 = new SeatDTO();
        seat1.setSeatNumber("1A");
        SeatDTO seat2 = new SeatDTO();
        seat2.setSeatNumber("1B");
        SeatDTO seat3 = new SeatDTO();
        seat3.setSeatNumber("1C");

        when(seatMapper.toDTO(seatA1)).thenReturn(seat1);
        when(seatMapper.toDTO(seatA2)).thenReturn(seat2);
        when(seatMapper.toDTO(seatA3)).thenReturn(seat3);

        List<SeatDTO> recommended = seatRecommendationService.recommendSeats(filter);
        assertNotNull(recommended);
        assertEquals(3, recommended.size());
        assertTrue(recommended.stream().anyMatch(dto -> "1A".equals(dto.getSeatNumber())));
        assertTrue(recommended.stream().anyMatch(dto -> "1B".equals(dto.getSeatNumber())));
        assertTrue(recommended.stream().anyMatch(dto -> "1C".equals(dto.getSeatNumber())));
    }
}
