package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.model.Seat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    Seat toEntity(SeatDTO seatDTO);
    SeatDTO toDTO(Seat seat);
}
