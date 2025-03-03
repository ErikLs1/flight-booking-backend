package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.SeatClassDTO;
import com.demo.flight_booking.model.SeatClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatClassMapper {
    SeatClass toEntity(SeatClassDTO seatClassDTO);
    SeatClassDTO toDTO(SeatClass seatClass);
}
