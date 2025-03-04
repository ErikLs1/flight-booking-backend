package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.SeatClassDTO;
import com.demo.flight_booking.model.SeatClass;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SeatClassMapper {
    SeatClassMapper INSTANCE = Mappers.getMapper(SeatClassMapper.class);

    SeatClassDTO toDTO(SeatClass seatClass);
    SeatClass toEntity(SeatClassDTO seatClassDTO);
}
