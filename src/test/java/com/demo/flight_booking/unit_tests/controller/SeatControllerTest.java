package com.demo.flight_booking.unit_tests.controller;

import com.demo.flight_booking.controller.SeatController;
import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.dto.filter.SeatRecommendationDTO;
import com.demo.flight_booking.mapper.SeatMapper;
import com.demo.flight_booking.repository.FlightSeatRepository;
import com.demo.flight_booking.service.SeatService;
import com.demo.flight_booking.service.impl.SeatRecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeatController.class)
public class SeatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SeatService seatService;

    @MockitoBean
    private SeatRecommendationService seatRecommendationService;

    @MockitoBean
    private FlightSeatRepository flightSeatRepository;

    @MockitoBean
    private SeatMapper seatMapper;

    @Test
    void testRecommendSeats() throws Exception {
        SeatRecommendationDTO filter = new SeatRecommendationDTO();
        filter.setFlightId(1L);
        filter.setAdjacentPreferred(true);
        filter.setPassengerCount(2);

        SeatDTO seat1 = new SeatDTO();
        seat1.setSeatNumber("1A");
        SeatDTO seat2 = new SeatDTO();
        seat2.setSeatNumber("1B");
        SeatDTO seat3 = new SeatDTO();
        seat3.setSeatNumber("1C");

        List<SeatDTO> recommendedSeats = Arrays.asList(seat1, seat2, seat3);

        when(seatRecommendationService.recommendSeats(any(SeatRecommendationDTO.class)))
                .thenReturn(recommendedSeats);

        mockMvc.perform(post("/api/seat/recommend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].seatNumber", is("1A")))
                .andExpect(jsonPath("$[1].seatNumber", is("1B")))
                .andExpect(jsonPath("$[2].seatNumber", is("1C")));
    }
}
