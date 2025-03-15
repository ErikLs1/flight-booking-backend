package com.demo.flight_booking.unit_tests.controller;

import com.demo.flight_booking.controller.BookingController;
import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.dto.booking.BookingRequestDTO;
import com.demo.flight_booking.dto.booking.BookingResponseDTO;
import com.demo.flight_booking.model.enums.PaymentMethod;
import com.demo.flight_booking.service.impl.BookingServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookingServiceImpl bookingService;

    @Test
    void testCreateBooking_Success() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setFirstName("Jane");
        person.setLastName("Smith");
        person.setEmail("jane.smith@gmail.com");
        person.setPhone("+123456");

        BookingRequestDTO request = new BookingRequestDTO();
        request.setFlightId(1L);
        request.setPassengers(Collections.singletonList(person));
        request.setSeatIds(Collections.singletonList(1L));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingId(1L);

        when(bookingService.bookFlight(any(BookingRequestDTO.class))).thenReturn(response);
        mvc.perform(post("/api/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookingId").value(1L));

        verify(bookingService).bookFlight(any(BookingRequestDTO.class));
    }

    @Test
    void testCreateBooking_BadRequest() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO();
        mvc.perform(post("/api/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
 }
