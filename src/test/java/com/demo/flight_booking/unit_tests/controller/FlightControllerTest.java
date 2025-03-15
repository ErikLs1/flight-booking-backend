package com.demo.flight_booking.unit_tests.controller;

import com.demo.flight_booking.controller.FlightController;
import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.SeatClassFeeDto;
import com.demo.flight_booking.dto.filter.FlightFilterDTO;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FlightService flightService;

    @Test
    void testGetAircraftModel() throws Exception {
        when(flightService.getAircraftModelByFlightId(1L)).thenReturn("Boeing 777");

        mockMvc.perform(get("/api/flight/1/aircraft-model"))
                .andExpect(status().isOk())
                .andExpect(content().string("Boeing 777"));
    }

    @Test
    void testGetSeatClasses() throws Exception {
        List<SeatClassType> seatClasses = Arrays.asList(SeatClassType.ECONOMY, SeatClassType.BUSINESS);
        when(flightService.getSeatClassesByFlightId(1L)).thenReturn(seatClasses);

        mockMvc.perform(get("/api/flight/1/seat-classes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("ECONOMY")))
                .andExpect(jsonPath("$[1]", is("BUSINESS")));
    }

    @Test
    void testGetFlightCities() throws Exception {
        List<String> cities = Arrays.asList("New York", "London");
        when(flightService.getFlightCitiesByFlightId(1L)).thenReturn(cities);

        mockMvc.perform(get("/api/flight/1/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("New York")))
                .andExpect(jsonPath("$[1]", is("London")));
    }

    @Test
    void testGetSeatClassFee() throws Exception {
        SeatClassFeeDto feeDto1 = new SeatClassFeeDto(SeatClassType.ECONOMY, 20.0);
        SeatClassFeeDto feeDto2 = new SeatClassFeeDto(SeatClassType.BUSINESS, 50.0);
        List<SeatClassFeeDto> feeDtos = Arrays.asList(feeDto1, feeDto2);

        when(flightService.getSeatClassesForFlight(1L)).thenReturn(feeDtos);

        mockMvc.perform(get("/api/flight/1/seat-class-fee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].seatClassName", is("ECONOMY")))
                .andExpect(jsonPath("$[0].baseFee", is(20.0)))
                .andExpect(jsonPath("$[1].seatClassName", is("BUSINESS")))
                .andExpect(jsonPath("$[1].baseFee", is(50.0)));
    }

    @Test
    void testFilterFlights() throws Exception {
        FlightFilterDTO filter = new FlightFilterDTO();
        filter.setDepartureCity("New York");
        filter.setArrivalCity("London");
        // Other filter fields as needed

        FlightDTO dto = new FlightDTO();
        dto.setFlightId(1L);
        dto.setFlightNumber("FL123");

        when(flightService.searchFlights(any(FlightFilterDTO.class))).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(post("/api/flight/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].flightId", is(1)))
                .andExpect(jsonPath("$[0].flightNumber", is("FL123")));
    }
}
